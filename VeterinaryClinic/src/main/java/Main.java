import repository.CrudRepository;
import repository.JdbcDoctorRepository;
import repository.JdbcPetRepository;
import repository.JdbcUserRepository;

public class Main {
    public static void main(String[] args) {
        CrudRepository jdbcDoctorRepository = new JdbcDoctorRepository();
        CrudRepository jdbcPetRepo = new JdbcUserRepository();

        System.out.println(jdbcPetRepo.findAll().toString());



    }
}