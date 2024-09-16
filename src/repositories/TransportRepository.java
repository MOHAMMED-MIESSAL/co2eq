package repositories;


import config.DatabaseConnection;
import enums.ConsumptionType;
import enums.VehiculeType;
import models.Transport;

import java.util.Optional;
import java.sql.*;


public class TransportRepository {


    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public boolean addTransport(Transport transport) {
        String insertConsumptionSql = "INSERT INTO consumptions (consumption, start_date, end_date, type, user_id) VALUES (?, ?, ?, ?::consumption_type, ?) RETURNING id";
        String insertTransportSql = "INSERT INTO transport (id, distance_parcourue, type_de_vehicule) VALUES (?, ?, ?::vehicle_type)";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(insertConsumptionSql);
             PreparedStatement pstmtTransport = conn.prepareStatement(insertTransportSql)) {

            // Obtenir le dernier ID à l'aide de la même connexion
            String getLastIdSql = "SELECT MAX(id) AS max_id FROM consumptions";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getLastIdSql)) {
                int newId = 0;
                if (rs.next()) {
                    newId = rs.getInt("max_id") + 1;
                }
                transport.setId(newId);
            }

            // Insertion dans la table des consommations
            pstmtConsumption.setDouble(1, transport.getConsumption());
            pstmtConsumption.setObject(2, transport.getStartDate());
            pstmtConsumption.setObject(3, transport.getEndDate());
            pstmtConsumption.setString(4, transport.getConsumptionType().name());
            pstmtConsumption.setString(5, transport.getUserId());

            // Exécuter la requête et récupérer l'ID généré
            ResultSet generatedKeys = pstmtConsumption.executeQuery();
            if (!generatedKeys.next()) {
                System.out.println("Erreur : Aucun ID généré pour la consommation.");
                return false;
            }
            transport.setId(generatedKeys.getInt(1));

            // Insertion dans la table des transports
            pstmtTransport.setInt(1, transport.getId());
            pstmtTransport.setDouble(2, transport.getDistanceParcourue());
            pstmtTransport.setString(3, transport.getTypeDeVehicule().name());

            int rowsAffectedTransport = pstmtTransport.executeUpdate();
            System.out.println("Consommation Transport ajouter avec succès !");
            return rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du transport: " + e.getMessage());
            return false;
        }
    }

    public Optional<Transport> getTransportById(int id) {
        String sql = "SELECT c.id, c.consumption, c.start_date, c.end_date, c.type, c.user_id, t.distance_parcourue, t.type_de_vehicule " +
                "FROM consumptions c JOIN transport t ON c.id = t.id WHERE c.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Transport transport = new Transport(
                        rs.getInt("id"),
                        rs.getDouble("consumption"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        ConsumptionType.valueOf(rs.getString("type")),
                        rs.getString("user_id"),
                        rs.getInt("distance_parcourue"),
                        VehiculeType.valueOf(rs.getString("type_de_vehicule"))
                );
                return Optional.of(transport);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du transport: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean updateTransport(int id, Transport transport) {
        String updateConsumptionSql = "UPDATE consumptions SET consumption = ?, start_date = ?, end_date = ?, type = ?::consumption_type, user_id = ? WHERE id = ?";
        String updateTransportSql = "UPDATE transport SET distance_parcourue = ?, type_de_vehicule = ?::vehicle_type WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(updateConsumptionSql);
             PreparedStatement pstmtTransport = conn.prepareStatement(updateTransportSql)) {

            // Mise à jour de la table des consommations
            pstmtConsumption.setDouble(1, transport.getConsumption());
            pstmtConsumption.setObject(2, transport.getStartDate());
            pstmtConsumption.setObject(3, transport.getEndDate());
            pstmtConsumption.setString(4, transport.getConsumptionType().name());
            pstmtConsumption.setString(5, transport.getUserId());
            pstmtConsumption.setInt(6, id);  // Utilisation du paramètre id pour la mise à jour

            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            // Mise à jour de la table des transports
            pstmtTransport.setDouble(1, transport.getDistanceParcourue());
            pstmtTransport.setString(2, transport.getTypeDeVehicule().name());
            pstmtTransport.setInt(3, id);  // Utilisation du paramètre id pour la mise à jour

            int rowsAffectedTransport = pstmtTransport.executeUpdate();

            System.out.println("Enregistrement modifier avec succes !");
            return rowsAffectedConsumption > 0 && rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du transport: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTransport(int id) {
        String deleteConsumptionSql = "DELETE FROM consumptions WHERE id = ?";
        String deleteTransportSql = "DELETE FROM transport WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(deleteConsumptionSql);
             PreparedStatement pstmtTransport = conn.prepareStatement(deleteTransportSql)) {

            // Supprimer l'enregistrement dans la table des transports
            pstmtTransport.setInt(1, id);
            int rowsAffectedTransport = pstmtTransport.executeUpdate();

            // Supprimer l'enregistrement dans la table des consommations
            pstmtConsumption.setInt(1, id);
            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            System.out.println("Enregistrement supprimer avec succes !");

            return rowsAffectedConsumption > 0 && rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du transport: " + e.getMessage());
            return false;
        }
    }

    //    public Transport getTransportById(int id) {
//        String sql = "SELECT c.id, c.consumption, c.start_date, c.end_date, c.type, c.user_id, t.distance_parcourue, t.type_de_vehicule " +
//                "FROM consumptions c JOIN transport t ON c.id = t.id WHERE c.id = ?";
//
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, id);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return new Transport(
//                        rs.getInt("id"),
//                        rs.getDouble("consumption"),
//                        rs.getTimestamp("start_date").toLocalDateTime(),
//                        rs.getTimestamp("end_date").toLocalDateTime(),
//                        ConsumptionType.valueOf(rs.getString("type")),
//                        rs.getString("user_id"),
//                        rs.getInt("distance_parcourue"),
//                        VehiculeType.valueOf(rs.getString("type_de_vehicule"))
//                );
//            } else {
//                System.out.println("Non enregistrement trouvé !");
//            }
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de la récupération du transport: " + e.getMessage());
//        }
//        return null;
//    }

}
