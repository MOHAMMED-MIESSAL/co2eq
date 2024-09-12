import enums.ConsumptionType;
import enums.VehiculeType;
import services.TransportService;
import services.UserService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Ajouter des utilisateurs à la base de données
        if (userService.addUser("John Doe", 25, "user123")) {
            System.out.println("Utilisateur ajouté avec succès.");
        }

        // Mettre à jour un utilisateur
//        if (userService.updateUser("1", "Johnathan Doe", 26)) {
//            System.out.println("Utilisateur mis à jour avec succès.");
//        }

        // Supprimer un utilisateur
//        if (userService.removeUser("1")) {
//            System.out.println("Utilisateur supprimé avec succès.");
//        }

        // Afficher les informations d'un utilisateur
//        userService.displayUser("1");

        // Verifier si un utilisateur existe
//        userService.userExists("1");


        TransportService transportService = new TransportService();

        // Définir les valeurs pour le nouveau transport
        double consumption = 126.34;
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 10, 0); // Exemple : 1er septembre 2024 à 10h00
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 5, 18, 0);   // Exemple : 5 septembre 2024 à 18h00
        ConsumptionType consumptionType = ConsumptionType.TRANSPORT;  // Assurez-vous que la valeur est correcte
        String userId = "user123"; // ID de l'utilisateur
        double distanceParcourue = 150.5; // Exemple de distance parcourue
        VehiculeType typeDeVehicule = VehiculeType.VOITURE; // Type de véhicule, assurez-vous que la valeur est correcte

        // Appel de la méthode addTransport avec les paramètres définis
        boolean result = transportService.addTransport(
                consumption,
                startDate,
                endDate,
                consumptionType,
                userId,
                distanceParcourue,
                typeDeVehicule
        );

        // Affichage du résultat
        if (result) {
            System.out.println("Transport ajouté avec succès.");
        } else {
            System.out.println("Erreur lors de l'ajout du transport.");
        }
    }
}
