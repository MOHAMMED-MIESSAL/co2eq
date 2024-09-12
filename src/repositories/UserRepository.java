package repositories;

import config.DatabaseConnection;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {

    // Méthode pour obtenir la connexion
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (id, name, age) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setInt(3, user.getAge());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, age = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getAge());
            pstmt.setString(3, user.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
            return false;
        }
    }

    public Optional<User> findUserById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getInt("age"), rs.getString("id"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            return Optional.empty();
        }
    }
}
