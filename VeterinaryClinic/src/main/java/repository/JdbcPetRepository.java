package repository;

import db.DatabaseConnection;
import domain.Gender;
import domain.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPetRepository implements PetRepository {

    private final String FIND_BY_OWNER_SQL = "SELECT * FROM pet WHERE owner_id = ?";

    private final String INSERT_SQL =
            "INSERT INTO pet(owner_id, name, species, breed, gender) VALUES (?, ?, ?, ?, ?)";

    private final String UPDATE_SQL =
            "UPDATE pet SET owner_id = ?, name = ?, species = ? breed = ? gender = ? WHERE id = ?";

    private final String DELETE_SQL = "DELETE FROM pet WHERE id = ?";
    private final String FIND_BY_ID_SQL = "SELECT * FROM pet WHERE id = ?";
    private final String FIND_ALL_SQL = "SELECT * FROM pet";

    @Override
    public Pet findByOwnerId(long ownerId) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_OWNER_SQL)) {

            ps.setLong(1, ownerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insert(Pet entity) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setLong(1, entity.getOwnerId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getSpecies());
            ps.setString(4, entity.getBreed());
            ps.setString(5, entity.getGender().toString().toLowerCase());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Pet entity) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {

            ps.setLong(1, entity.getOwnerId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getSpecies());
            ps.setString(4, entity.getBreed());
            ps.setString(5, entity.getGender().toString().toLowerCase());
            ps.setLong(6, id);

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
    public Pet findById(Long id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Pet> findAll() {
        List<Pet> doctors = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                doctors.add(mapPet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doctors;
    }

    private Pet mapPet(ResultSet rs) throws SQLException {
        return new Pet(
                rs.getLong("id"),
                rs.getLong("owner_id"),
                rs.getString("name"),
                rs.getString("species"),
                rs.getString("breed"),
                Gender.fromDb(rs.getString("gender"))
        );
    }
}
