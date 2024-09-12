package repositories;

import config.DatabaseConnection;
import models.Transport;
import enums.VehiculeType;
import enums.ConsumptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class TransportRepository {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

//    public boolean addTransport(Transport transport) {
//        String insertConsumptionSql = "INSERT INTO consumptions (consumption, start_date, end_date, type, user_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
//        String insertTransportSql = "INSERT INTO transport (id, distance_parcourue, type_de_vehicule) VALUES (?, ?, ?)";
//
//        try (Connection conn = getConnection();
//             PreparedStatement pstmtConsumption = conn.prepareStatement(insertConsumptionSql);
//             PreparedStatement pstmtTransport = conn.prepareStatement(insertTransportSql)) {
//
//            // Insertion dans la table des consommations
//            pstmtConsumption.setDouble(1, transport.getConsumption());
//            pstmtConsumption.setObject(2, transport.getStartDate());
//            pstmtConsumption.setObject(3, transport.getEndDate());
//            pstmtConsumption.setString(4, transport.getConsumptionType().name());
//            pstmtConsumption.setString(5, transport.getUserId());
//
//            // Exécuter la requête et récupérer l'ID généré
//            ResultSet generatedKeys = pstmtConsumption.executeQuery();
//            if (!generatedKeys.next()) {
//                return false;
//            }
//            int generatedId = generatedKeys.getInt(1);
//            transport.setId(generatedId);
//
//            // Insertion dans la table des transports
//            pstmtTransport.setInt(1, transport.getId());
//            pstmtTransport.setDouble(2, transport.getDistanceParcourue());
//            pstmtTransport.setString(3, transport.getTypeDeVehicule().name());
//
//            int rowsAffectedTransport = pstmtTransport.executeUpdate();
//            return rowsAffectedTransport > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de l'ajout du transport: " + e.getMessage());
//            return false;
//        }
//    }

    public boolean addTransport(Transport transport) {
        String insertConsumptionSql = "INSERT INTO consumptions (consumption, start_date, end_date, type, user_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
        String insertTransportSql = "INSERT INTO transport (id, distance_parcourue, type_de_vehicule) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(insertConsumptionSql);
             PreparedStatement pstmtTransport = conn.prepareStatement(insertTransportSql)) {

            // Insertion dans la table des consommations
            pstmtConsumption.setDouble(1, transport.getConsumption());
            pstmtConsumption.setObject(2, transport.getStartDate());
            pstmtConsumption.setObject(3, transport.getEndDate());
            pstmtConsumption.setObject(4, transport.getConsumptionType().name()); // Utilisation de `.name()` pour ENUM
            pstmtConsumption.setString(5, transport.getUserId());

            // Exécuter la requête et récupérer l'ID généré
            ResultSet generatedKeys = pstmtConsumption.executeQuery();
            if (!generatedKeys.next()) {
                return false;
            }
            int generatedId = generatedKeys.getInt(1);
            transport.setId(generatedId);

            // Insertion dans la table des transports
            pstmtTransport.setInt(1, transport.getId());
            pstmtTransport.setDouble(2, transport.getDistanceParcourue());
            pstmtTransport.setObject(3, transport.getTypeDeVehicule().name()); // Utilisation de `.name()` pour ENUM

            int rowsAffectedTransport = pstmtTransport.executeUpdate();
            return rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du transport: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTransport(Transport transport) {
        String updateConsumptionSql = "UPDATE consumptions SET consumption = ?, start_date = ?, end_date = ?, type = ?, user_id = ? WHERE id = ?";
        String updateTransportSql = "UPDATE transport SET distance_parcourue = ?, type_de_vehicule = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(updateConsumptionSql);
             PreparedStatement pstmtTransport = conn.prepareStatement(updateTransportSql)) {

            // Mise à jour dans la table des consommations
            pstmtConsumption.setDouble(1, transport.getConsumption());
            pstmtConsumption.setObject(2, transport.getStartDate());
            pstmtConsumption.setObject(3, transport.getEndDate());
            pstmtConsumption.setString(4, transport.getConsumptionType().name());
            pstmtConsumption.setString(5, transport.getUserId());
            pstmtConsumption.setInt(6, transport.getId());

            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            // Mise à jour dans la table des transports
            pstmtTransport.setDouble(1, transport.getDistanceParcourue());
            pstmtTransport.setString(2, transport.getTypeDeVehicule().name());
            pstmtTransport.setInt(3, transport.getId());

            int rowsAffectedTransport = pstmtTransport.executeUpdate();
            return rowsAffectedConsumption > 0 && rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du transport: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTransport(int id) {
        String deleteTransportSql = "DELETE FROM transport WHERE id = ?";
        String deleteConsumptionSql = "DELETE FROM consumptions WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtTransport = conn.prepareStatement(deleteTransportSql);
             PreparedStatement pstmtConsumption = conn.prepareStatement(deleteConsumptionSql)) {

            // Suppression dans la table des transports
            pstmtTransport.setInt(1, id);
            int rowsAffectedTransport = pstmtTransport.executeUpdate();

            // Suppression dans la table des consommations
            pstmtConsumption.setInt(1, id);
            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            return rowsAffectedTransport > 0 && rowsAffectedConsumption > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du transport: " + e.getMessage());
            return false;
        }
    }

    public Optional<Transport> findTransportById(int id) {
        String sql = "SELECT * FROM consumptions c JOIN transport t ON c.id = t.id WHERE t.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double consumption = rs.getDouble("consumption");
                LocalDateTime startDate = rs.getObject("start_date", LocalDateTime.class);
                LocalDateTime endDate = rs.getObject("end_date", LocalDateTime.class);
                ConsumptionType consumptionType = ConsumptionType.valueOf(rs.getString("type"));
                double distanceParcourue = rs.getDouble("distance_parcourue");
                VehiculeType typeDeVehicule = VehiculeType.valueOf(rs.getString("type_de_vehicule"));
                String userId = rs.getString("user_id");

                Transport transport = new Transport(id, consumption, startDate, endDate, consumptionType, userId, distanceParcourue, typeDeVehicule);
                return Optional.of(transport);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du transport: " + e.getMessage());
            return Optional.empty();
        }
    }
}
