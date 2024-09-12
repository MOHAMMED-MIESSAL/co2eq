package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // L'instance Singleton
    private static DatabaseConnection instance;
    private Connection connection;

    // URL de connexion à la base de données
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/greenpulse";
    private static final String USER = "GreenPulse";
    private static final String PASSWORD = ""; // Ajouter votre mot de passe si nécessaire

    // Constructeur privé pour empêcher la création d'instances supplémentaires
    private DatabaseConnection() throws SQLException {
        try {
            // Chargement du driver PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Établir la connexion
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Erreur de connexion à la base de données");
        }
    }

    // Méthode publique pour obtenir l'instance Singleton
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return connection;
    }
}
