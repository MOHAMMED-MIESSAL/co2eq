package repositories;

import config.DatabaseConnection;
import enums.AlimentType;
import enums.ConsumptionType;
import models.Alimentation;

import java.sql.*;
import java.util.Optional;

public class AlimentationRepository {

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public boolean addAlimentation(Alimentation alimentation) {
        String insertConsumptionSql = "INSERT INTO consumptions (consumption, start_date, end_date, type, user_id) VALUES (?, ?, ?, ?::consumption_type, ?) RETURNING id";
        String insertAlimentationSql = "INSERT INTO alimentation (id, weight, aliment_type) VALUES (?,?,?::aliment_type)";
        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(insertConsumptionSql);
             PreparedStatement pstmtAlimentation = conn.prepareStatement(insertAlimentationSql)) {

            // Obtenir le dernier ID à l'aide de la même connexion
            String getLastIdSql = "SELECT MAX(id) AS max_id FROM consumptions";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getLastIdSql)) {
                int newId = 0;
                if (rs.next()) {
                    newId = rs.getInt("max_id") + 1;
                }
                alimentation.setId(newId);
            }

            // Insertion dans la table des consommations
            pstmtConsumption.setDouble(1, alimentation.getConsumption());
            pstmtConsumption.setObject(2, alimentation.getStartDate());
            pstmtConsumption.setObject(3, alimentation.getEndDate());
            pstmtConsumption.setString(4, alimentation.getConsumptionType().name());
            pstmtConsumption.setString(5, alimentation.getUserId());

            // Exécuter la requête et récupérer l'ID généré
            ResultSet generatedKeys = pstmtConsumption.executeQuery();
            if (!generatedKeys.next()) {
                System.out.println("Erreur : Aucun ID généré pour la consommation.");
                return false;
            }
            alimentation.setId(generatedKeys.getInt(1));

            // Insertion dans la table des transports
            pstmtAlimentation.setInt(1, alimentation.getId());
            pstmtAlimentation.setDouble(2, alimentation.getWeight());
            pstmtAlimentation.setString(3, alimentation.getAlimentType().name());

            int rowsAffectedAlimentation = pstmtAlimentation.executeUpdate();
            return rowsAffectedAlimentation > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du logement: " + e.getMessage());
            return false;
        }
    }

    public Optional<Alimentation> getAlimentationById(int id) {
        String sql = "SELECT c.id, c.consumption, c.start_date, c.end_date, c.type, c.user_id, a.weight, a.aliment_type " +
                "FROM consumptions c JOIN alimentation a ON c.id = a.id WHERE c.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Alimentation alimentation = new Alimentation(
                        rs.getDouble("consumption"),
                        rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime(),
                        ConsumptionType.valueOf(rs.getString("type")),
                        rs.getString("user_id"),
                        AlimentType.valueOf(rs.getString("aliment_type")),
                        rs.getDouble("weight")
                );
                return Optional.of(alimentation);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du alimentation: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean updateAlimentation(int id, Alimentation alimentation) {
        String updateConsumptionSql = "UPDATE consumptions SET consumption = ?, start_date = ?, end_date = ?, type = ?::consumption_type, user_id = ? WHERE id = ?";
        String updateAlimentationSql = "UPDATE alimentation SET weight = ?, aliment_type = ?::aliment_type WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtConsumption = conn.prepareStatement(updateConsumptionSql);
             PreparedStatement pstmtAlimentation = conn.prepareStatement(updateAlimentationSql)) {

            // Mise à jour de la table des consommations
            pstmtConsumption.setDouble(1, alimentation.getConsumption());
            pstmtConsumption.setObject(2, alimentation.getStartDate());
            pstmtConsumption.setObject(3, alimentation.getEndDate());
            pstmtConsumption.setString(4, alimentation.getConsumptionType().name());
            pstmtConsumption.setString(5, alimentation.getUserId());
            pstmtConsumption.setInt(6, id);  // Utilisation du paramètre id pour la mise à jour

            int rowsAffectedConsumption = pstmtConsumption.executeUpdate();

            // Mise à jour de la table des transports
            pstmtAlimentation.setDouble(1, alimentation.getWeight());
            pstmtAlimentation.setString(2, alimentation.getAlimentType().name());
            pstmtAlimentation.setInt(3, id);  // Utilisation du paramètre id pour la mise à jour

            int rowsAffectedTransport = pstmtAlimentation.executeUpdate();

            return rowsAffectedConsumption > 0 && rowsAffectedTransport > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du logement: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAlimentation(int id) {
        String checkTypeSql = "SELECT type FROM consumptions WHERE id = ?";
        String deleteConsumptionSql = "DELETE FROM consumptions WHERE id = ?";
        String deleteAlimentationSql = "DELETE FROM alimentation WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmtCheckType = conn.prepareStatement(checkTypeSql);
             PreparedStatement pstmtConsumption = conn.prepareStatement(deleteConsumptionSql);
             PreparedStatement pstmtLogement = conn.prepareStatement(deleteAlimentationSql)) {

            // Vérifier si le type de consommation est bien ALIMENTATION
            pstmtCheckType.setInt(1, id);
            ResultSet rs = pstmtCheckType.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                if (!type.equalsIgnoreCase("ALIMENTATION")) {
                    System.out.println("Erreur : Le type de consommation n'est pas ALIMENTATION.");
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

            return rowsAffectedConsumption > 0 && rowsAffectedLogement > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du alimentation: " + e.getMessage());
            return false;
        }
    }
}
