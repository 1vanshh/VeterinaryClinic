import repository.CrudRepository;
import repository.JdbcDoctorRepository;
import repository.JdbcPetRepository;

public class Main {
    public static void main(String[] args) {
        CrudRepository jdbcDoctorRepository = new JdbcDoctorRepository();
        CrudRepository jdbcPetRepo = new JdbcPetRepository();

        System.out.println(jdbcPetRepo.findAll().toString());



    }
}