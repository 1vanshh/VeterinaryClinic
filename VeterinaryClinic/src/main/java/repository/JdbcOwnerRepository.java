package repository;

import db.DatabaseConnection;
import domain.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcOwnerRepository implements OwnerRepository {

    private static final String FIND_BY_PHONE_SQL = "SELECT * FROM owner WHERE phone = ?";
    private static final String INSERT_SQL =
            "INSERT INTO owner(full_name, phone, email, address) VALUES (?,?,?,?)";

    private static final String UPDATE_SQL =
            "UPDATE owner SET full_name = ?, phone = ?, email = ?, address = ? WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM owner WHERE id = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM owner WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM owner";

    @Override
    public Owner findByPhone(String phone) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_PHONE_SQL)) {

            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOwner(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(Owner entity) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getPhone());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getAddress());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Owner entity) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getPhone());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getAddress());
            ps.setLong(5, id);

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
    public Owner findById(Long id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOwner(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                owners.add(mapOwner(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owners;
    }

    private Owner mapOwner(ResultSet rs) throws SQLException {
        return new Owner(
                rs.getLong("id"),
                rs.getString("full_name"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("address")
        );
    }
}
