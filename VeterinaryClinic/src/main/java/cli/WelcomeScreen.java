package cli;

import cli.util.InputReader;
import domain.Role;
import service.AdminService;
import service.AppointmentService;
import service.OwnerService;
import service.authentication.AuthenticationResult;
import service.authentication.AuthenticationService;

public class WelcomeScreen {

    private final InputReader in;
    private final AuthenticationService auth;

    private final AdminService adminService;
    private final AppointmentService appointmentService;
    private final OwnerService ownerService;

    public WelcomeScreen(InputReader in,
                         AuthenticationService auth,
                         AdminService adminService,
                         AppointmentService appointmentService,
                         OwnerService ownerService) {
        this.in = in;
        this.auth = auth;
        this.adminService = adminService;
        this.appointmentService = appointmentService;
        this.ownerService = ownerService;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Ветеринарная клиника ===");
            System.out.println("Введите 'admin' или номер телефона.");
            System.out.println("0 = повторить экран, -1 = выход");

            String input = in.readLine("Логин: ");

            if ("-1".equals(input)) {
                System.out.println("Завершение программы.");
                return;
            }
            if ("0".equals(input)) {
                continue;
            }

            try {
                AuthenticationResult result = auth.login(input);
                route(result);
            } catch (Exception e) {
                System.out.println("Ошибка входа: " + e.getMessage());
            }
        }
    }

    private void route(AuthenticationResult r) {
        if (r.getRole() == Role.ADMIN) {
            new cli.admin.AdminMenu(in, adminService).run();
            return;
        }

        if (r.getRole() == Role.DOCTOR) {
            new cli.doctor.DoctorMenu(in, r.getDoctorId(), appointmentService).run();
            return;
        }

        if (r.getRole() == Role.OWNER) {
            // OwnerMenu ты сделаешь сам по аналогии
            new cli.owner.OwnerMenu(in, r.getOwnerId(), ownerService, appointmentService, adminService).run();
            return;
        }

        System.out.println("Неизвестная роль: " + r.getRole());
    }
}
