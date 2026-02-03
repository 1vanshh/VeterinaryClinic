package repository;

import db.DatabaseConnection;
import domain.Doctor;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JdbcDoctorRepository implements CrudRepository<Doctor, Long> {

    private final String INSERT_SQL = "INSERT INTO doctor(full_name, specialization, phone) VALUES (?,?,?)";
    private final String UPDATE_SQL = "UPDATE doctor SET full_name = ?, specialization = ?, phone = ? WHERE id = ?";
    private final String DELETE_SQL = "DELETE FROM doctor WHERE id = ?";
    private final String FIND_BY_ID_SQL = "SELECT * FROM doctor WHERE id = ?";
    private final String FIND_ALL_SQL = "SELECT * FROM doctor";

    @Override
    public void insert(Doctor entity) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getSpecialization());
            ps.setString(3, entity.getPhone());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Doctor entity) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getSpecialization());
            ps.setString(3, entity.getPhone());
            ps.setLong(4, id);

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
    public Doctor findById(Long id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDoctor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctors.add(mapDoctor(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctors;
    }

    private Doctor mapDoctor(ResultSet rs) throws SQLException {
        return new Doctor(
                rs.getLong("id"),
                rs.getString("full_name"),
                rs.getString("specialization"),
                rs.getString("phone")
        );
    }
}
