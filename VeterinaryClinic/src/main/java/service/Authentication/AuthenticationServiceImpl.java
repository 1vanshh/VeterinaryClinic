package service.Authentication;

import domain.Doctor;
import domain.Owner;
import repository.DoctorRepository;
import repository.OwnerRepository;

import static service.validator.PhoneValidator.validateAndNormalize;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final OwnerRepository ownerRepository;
    private final DoctorRepository doctorRepository;

    public AuthenticationServiceImpl(OwnerRepository ownerRepository, DoctorRepository doctorRepository) {
        this.ownerRepository = ownerRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public AuthenticationResult login(String input) {
        String s = input == null ? "" : input.trim();

        if ("admin".equalsIgnoreCase(s)) {
            return AuthenticationResult.admin();
        }

        String phone = validateAndNormalize(s);
        if (phone.isBlank()) {
            throw new IllegalArgumentException("Введите 'admin' или номер телефона");
        }

        Owner owner = ownerRepository.findByPhone(phone);
        if (owner != null) return AuthenticationResult.owner(owner.getId());

        Doctor doctor = doctorRepository.findByPhone(phone);
        if (doctor != null) return AuthenticationResult.doctor(doctor.getId());

        throw new IllegalArgumentException("Пользователь с таким телефоном не найден");
    }
}
