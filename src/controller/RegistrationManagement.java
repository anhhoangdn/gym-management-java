package controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Member;
import model.Package;
import model.Registration;
import model.RegistrationRow;
import repository.PackageRepository;
import repository.RegistrationRepository;
import repository.UserRepository;
import util.Operation;
import view.AddRegistrationDialog;
import view.RegistrationView;

public class RegistrationManagement implements Operation {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,###");

    private final RegistrationRepository registrationRepo;
    private final UserRepository userRepo;
    private final PackageRepository packageRepo;
    private RegistrationView view;

    public RegistrationManagement(RegistrationRepository registrationRepo,
                                  UserRepository userRepo,
                                  PackageRepository packageRepo) {
        this.registrationRepo = registrationRepo;
        this.userRepo = userRepo;
        this.packageRepo = packageRepo;
    }

    @Override
    public void execute() {
        if (view != null && view.isDisplayable()) {
            view.toFront();
            view.requestFocus();
            return;
        }

        view = new RegistrationView();
        view.onSearch(e -> loadData(view.getSearchField(), view.getSearchKeyword()));
        view.onReload(e -> loadData(null, null));
        view.onAdd(e -> openAddDialog(-1, -1));
        view.onRenew(e -> renewSelected());
        view.onDelete(e -> deleteSelected());

        loadData(null, null);
        view.setVisible(true);
    }

    private void loadData(String field, String keyword) {
        List<RegistrationRow> rows = (field == null || keyword == null || keyword.isBlank())
                ? registrationRepo.findAllJoined()
                : registrationRepo.searchByMemberKeyword(field, keyword);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (RegistrationRow r : rows) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getFullName(),
                    r.getUserEmail(),
                    r.getPackageName(),
                    formatDate(r.getStartDate()),
                    formatDate(r.getEndDate()),
                    MONEY_FORMAT.format(r.getTotal()),
                    r.getEffectiveStatusLabel()
            });
        }
    }

    // ===== ADD =====
    /**
     * Mở dialog tạo đăng ký mới. Nếu preselectUserId / preselectPackageId > 0
     * thì pre-fill (dùng khi chuyển từ Gia hạn của đăng ký đã hết hạn).
     */
    private void openAddDialog(int preselectUserId, int preselectPackageId) {
        AddRegistrationDialog dialog = new AddRegistrationDialog(view);

        // Load tất cả member ban đầu
        loadUsersIntoDialog(dialog, null, null);

        // Load chỉ gói active vào combo
        List<Package> active = packageRepo.findActivePackages();
        if (active.isEmpty()) {
            view.showError("Hiện không có gói nào đang hoạt động. Vui lòng thêm/kích hoạt gói trước.");
            dialog.dispose();
            return;
        }
        List<Integer> packageIds = new ArrayList<>();
        List<Double> packagePrices = new ArrayList<>();
        List<Integer> packageDurations = new ArrayList<>();
        for (Package p : active) {
            dialog.getPackageCombo().addItem(buildPackageLabel(p));
            packageIds.add(p.getId());
            packagePrices.add(p.getPrice());
            packageDurations.add(p.getDuration());
        }

        dialog.setStartDateText(LocalDate.now().toString());

        // Tự cập nhật endDate + total khi user đổi gói hoặc đổi ngày bắt đầu
        dialog.getPackageCombo().addActionListener(
                e -> recomputeEndDateAndTotal(dialog, packageDurations, packagePrices));
        dialog.getStartDateField().getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    @Override public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        recomputeEndDateAndTotal(dialog, packageDurations, packagePrices);
                    }
                    @Override public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        recomputeEndDateAndTotal(dialog, packageDurations, packagePrices);
                    }
                    @Override public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        recomputeEndDateAndTotal(dialog, packageDurations, packagePrices);
                    }
                });

        // Cập nhật label khi chọn user
        dialog.getUserTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int uid = dialog.getSelectedUserId();
            if (uid > 0) {
                int row = dialog.getUserTable().getSelectedRow();
                String fn = String.valueOf(dialog.getUserTableModel().getValueAt(row, 1));
                String ln = String.valueOf(dialog.getUserTableModel().getValueAt(row, 2));
                dialog.setSelectedUserLabel("Đã chọn: " + fn + " " + ln + " (ID = " + uid + ")");
            } else {
                dialog.setSelectedUserLabel("Chưa chọn hội viên");
            }
        });

        dialog.onSearch(e -> loadUsersIntoDialog(dialog, dialog.getSearchField(), dialog.getSearchKeyword()));
        dialog.onConfirm(e -> {
            int uid = dialog.getSelectedUserId();
            if (uid <= 0) {
                dialog.showError("Vui lòng chọn hội viên từ bảng.");
                return;
            }
            int idx = dialog.getPackageCombo().getSelectedIndex();
            if (idx < 0) {
                dialog.showError("Vui lòng chọn gói tập.");
                return;
            }
            LocalDate start;
            try {
                start = LocalDate.parse(dialog.getStartDateText());
            } catch (Exception ex) {
                dialog.showError("Ngày bắt đầu không hợp lệ (định dạng yyyy-MM-dd).");
                return;
            }

            // Chặn trùng: hội viên này đã có đăng ký còn hiệu lực chưa?
            List<RegistrationRow> activeRegs = registrationRepo.findActiveByUserId(uid);
            if (!activeRegs.isEmpty()) {
                RegistrationRow existing = activeRegs.get(0);
                dialog.showError("Hội viên này đang có gói \"" + existing.getPackageName() +
                        "\" còn hiệu lực đến " + formatDate(existing.getEndDate()) +
                        ".\nVui lòng dùng chức năng \"Gia hạn\" thay vì tạo mới.");
                return;
            }

            int pkgId = packageIds.get(idx);
            int duration = packageDurations.get(idx);
            double price = packagePrices.get(idx);
            LocalDate end = start.plusMonths(duration);

            Registration reg = new Registration();
            reg.setUserId(uid);
            reg.setPackageId(pkgId);
            reg.setStartDate(java.sql.Date.valueOf(start));
            reg.setEndDate(java.sql.Date.valueOf(end));
            reg.setTotal(price);
            reg.setStatus(1);

            int newId = registrationRepo.createRegistration(reg);
            if (newId > 0) {
                dialog.showMessage("Tạo đăng ký thành công! ID = " + newId);
                dialog.dispose();
                loadData(null, null);
            } else {
                dialog.showError("Tạo đăng ký thất bại.");
            }
        });

        // Pre-select user trong bảng (nếu có)
        if (preselectUserId > 0) {
            selectRowByFirstColumn(dialog.getUserTableModel(), dialog.getUserTable(), preselectUserId);
        }
        // Pre-select gói trong combo (nếu có và gói còn active)
        if (preselectPackageId > 0) {
            int idx = packageIds.indexOf(preselectPackageId);
            if (idx >= 0) {
                dialog.getPackageCombo().setSelectedIndex(idx);
            }
        }
        // Tính lại endDate + total sau khi đã set combo (nếu có)
        recomputeEndDateAndTotal(dialog, packageDurations, packagePrices);

        dialog.setVisible(true);
    }

    private void loadUsersIntoDialog(AddRegistrationDialog dialog, String field, String keyword) {
        List<Member> members = (field == null || keyword == null || keyword.isBlank())
                ? userRepo.findAllMembers()
                : userRepo.searchMembers(field, keyword);

        DefaultTableModel model = dialog.getUserTableModel();
        model.setRowCount(0);
        for (Member m : members) {
            model.addRow(new Object[]{m.getId(), m.getFirstName(), m.getLastName(), m.getEmail()});
        }
        dialog.setSelectedUserLabel("Chưa chọn hội viên");
    }

    /** Chọn dòng trong bảng theo giá trị ở cột 0 (ID). */
    private void selectRowByFirstColumn(DefaultTableModel model, javax.swing.JTable table, int targetId) {
        for (int i = 0; i < model.getRowCount(); i++) {
            Object v = model.getValueAt(i, 0);
            int id;
            if (v instanceof Integer) {
                id = (Integer) v;
            } else {
                try { id = Integer.parseInt(v.toString()); } catch (Exception ex) { continue; }
            }
            if (id == targetId) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                return;
            }
        }
    }

    // ===== RENEW =====
    private void renewSelected() {
        int regId = view.getSelectedRegistrationId();
        if (regId < 0) {
            view.showError("Vui lòng chọn một đăng ký trong bảng trước.");
            return;
        }
        Registration reg = registrationRepo.findById(regId);
        if (reg == null) {
            view.showError("Không tìm thấy đăng ký ID = " + regId);
            return;
        }
        if (reg.getEndDate() == null) {
            view.showError("Đăng ký thiếu ngày kết thúc, không thể gia hạn.");
            return;
        }

        // === TRƯỜNG HỢP 1: Đăng ký đã hết hạn -> coi như tạo đăng ký mới ===
        if (reg.isExpired()) {
            view.showMessage("Đăng ký này đã hết hạn.\n" +
                    "Sẽ mở form tạo đăng ký mới với hội viên và gói cũ được điền sẵn.\n" +
                    "Bạn có thể đổi gói nếu muốn.");
            openAddDialog(reg.getUserId(), reg.getPackageId());
            return;
        }

        // === TRƯỜNG HỢP 2: Đăng ký còn hạn -> cộng thêm thời gian vào row hiện tại ===
        Package pkg = packageRepo.findById(reg.getPackageId());
        if (pkg == null) {
            view.showError("Không tìm thấy gói tập của đăng ký này.");
            return;
        }
        if (pkg.getStatus() != 1) {
            view.showError("Gói \"" + pkg.getPackageName() +
                    "\" đã ngừng hoạt động, không thể gia hạn.\n" +
                    "Vui lòng kích hoạt lại gói trong mục Quản lý Gói tập.");
            return;
        }
        if (pkg.getDuration() <= 0) {
            view.showError("Thời hạn gói tập không hợp lệ.");
            return;
        }

        LocalDate currentEnd = toLocalDate(reg.getEndDate());
        LocalDate newEnd = currentEnd.plusMonths(pkg.getDuration());
        double newTotal = reg.getTotal() + pkg.getPrice();

        String msg = "Gia hạn gói \"" + pkg.getPackageName() + "\" cho đăng ký ID = " + regId + "?" +
                "\n- Ngày kết thúc cũ: " + currentEnd +
                "\n- Ngày kết thúc mới: " + newEnd +
                "\n- Tổng tiền mới: " + MONEY_FORMAT.format(newTotal) + " VNĐ";
        if (!view.confirm(msg, "Xác nhận gia hạn")) {
            return;
        }

        boolean ok = registrationRepo.renewRegistration(regId, java.sql.Date.valueOf(newEnd), newTotal);
        if (ok) {
            view.showMessage("Gia hạn thành công. Ngày kết thúc mới: " + newEnd);
            loadData(null, null);
        } else {
            view.showError("Gia hạn thất bại.");
        }
    }

    // ===== DELETE =====
    private void deleteSelected() {
        int regId = view.getSelectedRegistrationId();
        if (regId < 0) {
            view.showError("Vui lòng chọn một đăng ký trong bảng trước.");
            return;
        }
        Registration reg = registrationRepo.findById(regId);
        if (reg == null) {
            view.showError("Không tìm thấy đăng ký ID = " + regId);
            return;
        }

        String label = "đăng ký ID = " + regId;
        Package pkg = packageRepo.findById(reg.getPackageId());
        if (pkg != null && pkg.getPackageName() != null) {
            label += " (" + pkg.getPackageName() + ")";
        }

        String state = reg.isExpired() ? "đã hết hạn" : "đang còn hạn";
        if (!view.confirm("Bạn có chắc xóa " + label + " - " + state + "?",
                "Xác nhận xóa đăng ký")) {
            return;
        }

        boolean ok = registrationRepo.deleteRegistration(regId);
        if (ok) {
            view.showMessage("Đã xóa đăng ký.");
            loadData(null, null);
        } else {
            view.showError("Xóa thất bại.");
        }
    }

    // ===== Helpers =====
    private String formatDate(Date date) {
        return date == null ? "-" : DATE_FORMAT.format(date);
    }

    private LocalDate toLocalDate(Date date) {
        // java.sql.Date.toInstant() ném UnsupportedOperationException vì sql.Date
        // chỉ có ngày, không có giờ. Phải chuyển qua sql.Date.toLocalDate().
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private String buildPackageLabel(Package p) {
        return p.getPackageName() + " - " + p.getDuration() + " tháng - " +
                MONEY_FORMAT.format(p.getPrice()) + " VNĐ";
    }

    // Tự tính ngày kết thúc và tổng tiền dựa trên gói đang chọn + ngày bắt đầu.
    private void recomputeEndDateAndTotal(AddRegistrationDialog dialog,
                                          List<Integer> durations,
                                          List<Double> prices) {
        int idx = dialog.getPackageCombo().getSelectedIndex();
        if (idx < 0) {
            return;
        }
        try {
            LocalDate start = LocalDate.parse(dialog.getStartDateText());
            LocalDate end = start.plusMonths(durations.get(idx));
            dialog.setEndDateLabel(end.toString());
            dialog.setTotalLabel(MONEY_FORMAT.format(prices.get(idx)) + " VNĐ");
        } catch (Exception ex) {
            dialog.setEndDateLabel("(ngày bắt đầu không hợp lệ)");
            dialog.setTotalLabel("-");
        }
    }
}
