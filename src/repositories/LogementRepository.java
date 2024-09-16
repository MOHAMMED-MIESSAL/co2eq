package repositories;

import config.DatabaseConnection;
import enums.ConsumptionType;
import enums.EnergyType;
import models.Logement;

import java.sql.*;
import java.util.Optional;

public class LogementRepository {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public boolean addLogement(Logement logement) {
        String insertConsumptionSql = "INSERT INTO consumptions (consumption, start_date, end_date, type, user_id) VALUES (?, ?, ?, ?::consumption_type, ?) RETURNING id";
        String insertLogementSql = "INSERT INTO logement (id, consommation_energie, type_energie) VALUES (?,?,?::energy_type)";
        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(insertConsumptionSql);
             PreparedStatement pstmtLogement = conn.prepareStatement(insertLogementSql)) {

            // Obtenir le dernier ID à l'aide de la même connexion
            String getLastIdSql = "SELECT MAX(id) AS max_id FROM consumptions";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getLastIdSql)) {
                int newId = 0;
                if (rs.next()) {
                    newId = rs.getInt("max_id") + 1;
                }
                logement.setId(newId);
            }

            // Insertion dans la table des consommations
            pstmtConsumption.setDouble(1, logement.getConsumption());
            pstmtConsumption.setObject(2, logement.getStartDate());
            pstmtConsumption.setObject(3, logement.getEndDate());
            pstmtConsumption.setString(4, logement.getConsumptionType().name());
            pstmtConsumption.setString(5, logement.getUserId());

            // Exécuter la requête et récupérer l'ID généré
            ResultSet generatedKeys = pstmtConsumption.executeQuery();
            if (!generatedKeys.next()) {
                System.out.println("Erreur : Aucun ID généré pour la consommation.");
                return false;
            }
            logement.setId(generatedKeys.getInt(1));

            // Insertion dans la table des transports
            pstmtLogement.setInt(1, logement.getId());
            pstmtLogement.setDouble(2, logement.getEnergyConsumption());
            pstmtLogement.setString(3, logement.getEnergyType().name());

            int rowsAffectedLogement = pstmtLogement.executeUpdate();
            System.out.println("Consommation Logement ajoutée avec succès !");
            return rowsAffectedLogement > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du logement: " + e.getMessage());
            return false;
        }
    }

    public Optional<Logement> getLogementById(int id) {
        String sql = "SELECT c.id, c.consumption, c.start_date, c.end_date, c.type, c.user_id, l.consommation_energie, l.type_energie " +
                "FROM consumptions c JOIN logement l ON c.id = l.id WHERE c.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Logement logement = new Logement(
                        rs.getDouble("consumption"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        ConsumptionType.valueOf(rs.getString("type")),
                        rs.getString("user_id"),
                        rs.getDouble("consommation_energie"),
                        EnergyType.valueOf(rs.getString("type_energie"))
                );
                return Optional.of(logement);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du logement: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean updateLogement(int id, Logement logement) {
        String updateConsumptionSql = "UPDATE consumptions SET consumption = ?, start_date = ?, end_date = ?, type = ?::consumption_type, user_id = ? WHERE id = ?";
        String updateLogementSql = "UPDATE logement SET consommation_energie = ?, type_energie = ?::energy_type WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(updateConsumptionSql);
             PreparedStatement pstmtLogement = conn.prepareStatement(updateLogementSql)) {

            // Mise à jour de la table des consommations
            pstmtConsumption.setDouble(1, logement.getConsumption());
            pstmtConsumption.setObject(2, logement.getStartDate());
            pstmtConsumption.setObject(3, logement.getEndDate());
            pstmtConsumption.setString(4, logement.getConsumptionType().name());
            pstmtConsumption.setString(5, logement.getUserId());
            pstmtConsumption.setInt(6, id);  // Utilisation du paramètre id pour la mise à jour

            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            // Mise à jour de la table des transports
            pstmtLogement.setDouble(1, logement.getEnergyConsumption());
            pstmtLogement.setString(2, logement.getEnergyType().name());
            pstmtLogement.setInt(3, id);  // Utilisation du paramètre id pour la mise à jour

            int rowsAffectedTransport = pstmtLogement.executeUpdate();

            System.out.println("Enregistrement modifier avec succes !");
            return rowsAffectedConsumption > 0 && rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du logement: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteLogement(int id) {
        String checkTypeSql = "SELECT type FROM consumptions WHERE id = ?";
        String deleteConsumptionSql = "DELETE FROM consumptions WHERE id = ?";
        String deleteLogementSql = "DELETE FROM logement WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtCheckType = conn.prepareStatement(checkTypeSql);
             PreparedStatement pstmtConsumption = conn.prepareStatement(deleteConsumptionSql);
             PreparedStatement pstmtLogement = conn.prepareStatement(deleteLogementSql)) {

            // Vérifier si le type de consommation est bien LOGEMENT
            pstmtCheckType.setInt(1, id);
            ResultSet rs = pstmtCheckType.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                if (!type.equalsIgnoreCase("LOGEMENT")) {
                    System.out.println("Erreur : Le type de consommation n'est pas LOGEMENT.");
                    return false;
                }
            } else {
                System.out.println("Erreur : Aucun enregistrement trouvé avec cet ID.");
                return false;
            }

            // Supprimer l'enregistrement dans la table des logements
            pstmtLogement.setInt(1, id);
            int rowsAffectedLogement = pstmtLogement.executeUpdate();

            // Supprimer l'enregistrement dans la table des consommations
            pstmtConsumption.setInt(1, id);
            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            System.out.println("Enregistrement supprimé avec succès !");
            return rowsAffectedConsumption > 0 && rowsAffectedLogement > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du logement: " + e.getMessage());
            return false;
        }
    }

}
