package repositories;

import config.DatabaseConnection;
import enums.AlimentType;
import enums.ConsumptionType;
import enums.EnergyType;
import enums.VehiculeType;
import models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    // Méthode pour obtenir tous les utilisateurs et leurs consommations totales
    public List<User> getUsersWithTotalConsumption() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.age, SUM(c.consumption) AS total_consumption " +
                "FROM users u " +
                "JOIN consumptions c ON u.id = c.user_id " +
                "GROUP BY u.id, u.name, u.age";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("id")
                );
                user.setTotalConsumption(rs.getDouble("total_consumption"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }

        return users;
    }

    // Méthode pour obtenir les consommations d'un utilisateur entre deux dates
    public List<Consumption> getUserConsumptionsBetweenDates(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Consumption> consumptions = new ArrayList<>();
        String sql = "SELECT c.id, c.consumption, c.start_date, c.end_date, c.type, a.weight, a.aliment_type, t.distance_parcourue, t.type_de_vehicule, l.consommation_energie, l.type_energie " +
                "FROM consumptions c " +
                "LEFT JOIN alimentation a ON c.id = a.id " +
                "LEFT JOIN transport t ON c.id = t.id " +
                "LEFT JOIN logement l ON c.id = l.id " +
                "WHERE c.user_id = ? AND c.start_date >= ? AND c.end_date <= ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setTimestamp(2, Timestamp.valueOf(startDate));
            pstmt.setTimestamp(3, Timestamp.valueOf(endDate));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");

                switch (type) {
                    case "ALIMENTATION":
                        consumptions.add(new Alimentation(
                                rs.getDouble("consumption"),
                                rs.getTimestamp("start_date").toLocalDateTime(),
                                rs.getTimestamp("end_date").toLocalDateTime(),
                                ConsumptionType.valueOf(type),
                                userId,
                                AlimentType.valueOf(rs.getString("aliment_type")),
                                rs.getDouble("weight")
                        ));
                        break;
                    case "TRANSPORT":
                        consumptions.add(new Transport(
                                rs.getDouble("consumption"),
                                rs.getTimestamp("start_date").toLocalDateTime(),
                                rs.getTimestamp("end_date").toLocalDateTime(),
                                ConsumptionType.valueOf(type),
                                userId,
                                rs.getInt("distance_parcourue"),
                                VehiculeType.valueOf(rs.getString("type_de_vehicule"))
                        ));
                        break;
                    case "LOGEMENT":
                        consumptions.add(new Logement(
                                rs.getDouble("consumption"),
                                rs.getTimestamp("start_date").toLocalDateTime(),
                                rs.getTimestamp("end_date").toLocalDateTime(),
                                ConsumptionType.valueOf(type),
                                userId,
                                rs.getDouble("consommation_energie"),
                                EnergyType.valueOf(rs.getString("type_energie"))
                        ));
                        break;
                    default:
                        // Gérer le cas où le type est inconnu
                        System.out.println("Type de consommation inconnu : " + type);
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des consommations : " + e.getMessage());
        }

        return consumptions;
    }

    // Méthode pour obtenir tous les utilisateurs
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, age FROM users";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                users.add(new User(name, age, id));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }

        return users;
    }

    // Inactif users
    public List<User> getInactiveUsers(LocalDateTime startDate, LocalDateTime endDate) {
        List<User> inactiveUsers = new ArrayList<>();

        // Requête pour récupérer les utilisateurs n'ayant pas de consommation chevauchant l'intervalle
        String sql = "SELECT u.id, u.name, u.age " +
                "FROM users u " +
                "WHERE NOT EXISTS (" +
                "    SELECT 1 FROM consumptions c " +
                "    WHERE u.id = c.user_id " +
                "    AND c.start_date <= ? " +
                "    AND c.end_date >= ?" +
                ")";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(endDate));   // La consommation ne doit pas se terminer après la fin de l'intervalle
            pstmt.setTimestamp(2, Timestamp.valueOf(startDate)); // La consommation ne doit pas commencer avant le début de l'intervalle

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");

                User user = new User(name, age, id);
                inactiveUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la détection des utilisateurs inactifs : " + e.getMessage());
        }

        return inactiveUsers;
    }


    // Tri Users By total Consumptions
    public List<User> sortUsersByTotalConsumption() {
        List<User> users = getUsersWithTotalConsumption();  // Appel à la méthode ci-dessus

        // Trier les utilisateurs par consommation totale décroissante
        return users.stream()
                .sorted(Comparator.comparingDouble(User::getTotalConsumption).reversed())
                .collect(Collectors.toList());
    }


}
