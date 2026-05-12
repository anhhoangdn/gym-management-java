package util;

import model.User;

public final class UserDisplayHelper {

    private UserDisplayHelper() {}

    public static String buildMemberLabel(User user, int userId) {
        String name = resolveName(user);
        if (name.isEmpty()) {
            return "ID = " + userId;
        }
        return name + " (ID = " + userId + ")";
    }

    public static String buildMemberName(User user) {
        if (user == null) {
            return "Không rõ";
        }
        String name = resolveName(user);
        return name.isEmpty() ? "Chưa cập nhật" : name;
    }

    private static String resolveName(User user) {
        if (user == null) {
            return "";
        }
        String firstName = user.getFirstName() != null ? user.getFirstName().trim() : "";
        String lastName = user.getLastName() != null ? user.getLastName().trim() : "";
        return (firstName + " " + lastName).trim();
    }
}
