package cli;

import cli.util.InputReader;
import repository.*;
import service.*;
import service.authentication.AuthenticationService;
import service.authentication.AuthenticationServiceImpl;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final InputReader in;

    private final AuthenticationService auth;
    private final AdminService adminService;
    private final AppointmentService appointmentService;
    private final OwnerService ownerService;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.in = new InputReader(scanner);

        // Repositories
        AppointmentRepository appointmentRepository = new JdbcAppointmentRepository();
        DoctorRepository doctorRepository = new JdbcDoctorRepository();
        OwnerRepository ownerRepository = new JdbcOwnerRepository();
        PetRepository petRepository = new JdbcPetRepository();

        // Services
        this.auth = new AuthenticationServiceImpl(ownerRepository, doctorRepository);
        this.adminService = new AdminServiceImpl(ownerRepository, doctorRepository, petRepository, appointmentRepository);
        this.appointmentService = new AppointmentServiceImpl(appointmentRepository, petRepository, doctorRepository);
        this.ownerService = new OwnerServiceImpl(petRepository);
    }

    public void run() {
        new WelcomeScreen(in, auth, adminService, appointmentService, ownerService).run();
    }
}
