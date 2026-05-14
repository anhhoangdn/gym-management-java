package controller;

import java.util.List;
import model.Registration;
import repository.PackageRepository;
import repository.RegistrationRepository;
import repository.UserRepository;
import util.Operation;
import view.StatisticsView;

public class ShowStatistics implements Operation {

    private final UserRepository userRepo;
    private final PackageRepository packageRepo;
    private final RegistrationRepository regRepo;

    public ShowStatistics(UserRepository userRepo, PackageRepository packageRepo, RegistrationRepository regRepo) {
        this.userRepo = userRepo;
        this.packageRepo = packageRepo;
        this.regRepo = regRepo;
    }

    @Override
    public void execute() {
        StatisticsView view = new StatisticsView();
        
        int totalMembers = userRepo.findAllMembers().size();
        int activePackages = packageRepo.findActivePackages().size();
        
        List<Registration> registrations = regRepo.findAll();
        int totalRegistrations = registrations.size();
        
        double totalRevenue = 0;
        for (Registration reg : registrations) {
            if (reg.getStatus() == 1) { // Assuming 1 means paid/active
                totalRevenue += reg.getTotal();
            }
        }
        
        view.setStatistics(totalRevenue, totalMembers, activePackages, totalRegistrations);
        view.setVisible(true);
    }
}
