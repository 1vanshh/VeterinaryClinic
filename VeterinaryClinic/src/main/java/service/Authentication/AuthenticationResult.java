package service.Authentication;

import domain.Role;

public class AuthenticationResult {

    private final Role role;
    private final Long ownerId;
    private final Long doctorId;

    private AuthenticationResult(Role role, Long ownerId, Long doctorId) {
        this.role = role;
        this.ownerId = ownerId;
        this.doctorId = doctorId;
    }

    public static AuthenticationResult admin() { return new AuthenticationResult(Role.ADMIN, null, null); }
    public static AuthenticationResult owner(long ownerId) { return new AuthenticationResult(Role.OWNER, ownerId, null); }
    public static AuthenticationResult doctor(long doctorId) { return new AuthenticationResult(Role.DOCTOR, null, doctorId); }

    public Role getRole() { return role; }
    public Long getOwnerId() { return ownerId; }
    public Long getDoctorId() { return doctorId; }
}
