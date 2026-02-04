package repository;

import db.DatabaseConnection;
import domain.Appointment;
import domain.AppointmentStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcAppointmentRepository implements AppointmentRepository {


    private static final String FIND_BY_DOCTOR_SQL = "SELECT * FROM appointment WHERE doctor_id = ?";
    private static final String FIND_BY_PET_SQL = "SELECT * FROM appointment WHERE pet_id = ?";
    private static final String EXISTS_OVERLAP_SQL =
            "SELECT EXISTS(SELECT 1 FROM appointment WHERE doctor_id = ? AND status IN ('planned', 'no_show') AND starts_at < ? AND ends_at > ?)";

    private static final String EXISTS_BY_DOCTOR_SQL =
            "SELECT EXISTS(SELECT 1 FROM appointment WHERE doctor_id = ? AND starts_at = ?)";

    private static final String INSERT_SQL =
            "INSERT INTO appointment(pet_id, doctor_id, starts_at, ends_at, reason, status) VALUES (?,?,?,?,?,?)";

    private static final String UPDATE_SQL =
            "UPDATE appointment SET pet_id = ?, doctor_id = ?, starts_at = ?, ends_at = ?, reason = ?, status = ? WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM appointment WHERE id = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM appointment WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM appointment";

    @Override
    public List<Appointment> findByDoctorId(long doctorId) {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_DOCTOR_SQL)) {

            ps.setLong(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapAppointment(rs));
                }
            }
            return appointments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appointment> findByPetId(long petId) {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_PET_SQL)) {

            ps.setLong(1, petId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapAppointment(rs));
                }
            }
            return appointments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByDoctorAndTime(long doctorId, OffsetDateTime startsAt) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(EXISTS_BY_DOCTOR_SQL)) {

            ps.setLong(1, doctorId);
            ps.setObject(2, startsAt);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsOverlapping(long doctorId, OffsetDateTime start, OffsetDateTime end) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(EXISTS_OVERLAP_SQL)) {

            ps.setLong(1, doctorId);
            ps.setObject(2, end);
            ps.setObject(3, start);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Appointment entity) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setLong(1, entity.getPetId());
            ps.setLong(2, entity.getDoctorId());
            ps.setObject(3, entity.getStartsAt());
            ps.setObject(4, entity.getEndsAt());
            ps.setString(5, entity.getReason());
            ps.setString(6, entity.getStatus() == null ? AppointmentStatus.PLANNED.dbValue() : entity.getStatus().dbValue());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Appointment entity) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setLong(1, entity.getPetId());
            ps.setLong(2, entity.getDoctorId());
            ps.setObject(3, entity.getStartsAt());
            ps.setObject(4, entity.getEndsAt());
            ps.setString(5, entity.getReason());
            ps.setString(6, entity.getStatus() == null
                    ? AppointmentStatus.PLANNED.dbValue()
                    : entity.getStatus().dbValue());

            ps.setLong(7, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appointment findById(Long id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAppointment(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                appointments.add(mapAppointment(rs));
            }
            return appointments;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Appointment mapAppointment(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getLong("id"),
                rs.getLong("pet_id"),
                rs.getLong("doctor_id"),
                rs.getObject("starts_at", OffsetDateTime.class),
                rs.getObject("ends_at", OffsetDateTime.class),
                rs.getString("reason"),
                AppointmentStatus.fromDb(rs.getString("status"))
        );
    }
}
