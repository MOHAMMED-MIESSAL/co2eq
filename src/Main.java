import enums.ConsumptionType;
import enums.VehiculeType;
import models.Transport;
import services.TransportService;
import services.UserService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
//        UserService userService = new UserService();

//        // Supprimer un utilisateur
//        if (userService.removeUser("USER123")) {
//            System.out.println("Utilisateur supprimé avec succès.");
//        }

        // Ajouter des utilisateurs à la base de données
//        if (userService.addUser("John Doe", 25, "USER123")) {
//            System.out.println("Utilisateur ajouté avec succès.");
//        }


//        // Définir les données pour un transport
//        double consumption = 126.34;
//        LocalDateTime startDate = LocalDateTime.of(2024, 9, 13, 10, 0);
//        LocalDateTime endDate = LocalDateTime.of(2024, 9, 13, 15, 0);
//        ConsumptionType consumptionType = ConsumptionType.TRANSPORT;
//        String userId = "USER123";  // Exemple d'ID utilisateur
//        int distanceParcourue = 200;  // Distance parcourue
//        VehiculeType typeDeVehicule = VehiculeType.TRAIN;  // Type de véhicule
//
//        // Créer un objet Transport sans spécifier l'ID (le repository s'en occupera)
//        Transport transport = new Transport(0, consumption, startDate, endDate, consumptionType, userId, distanceParcourue, typeDeVehicule);


//        //     Définir les données pour un transport
//        double Updatedconsumption = 890.5;
//        LocalDateTime UpdatedstartDate = LocalDateTime.of(2024, 10, 10, 00, 0);
//        LocalDateTime UpdatedendDate = LocalDateTime.of(2024, 11, 11, 00, 0);
//        ConsumptionType UpdatedconsumptionType = ConsumptionType.TRANSPORT;
//        String UpdateduserId = "USER123";  // Exemple d'ID utilisateur
//        int UpdateddistanceParcourue = 150;  // Distance parcourue
//        VehiculeType UpdatedtypeDeVehicule = VehiculeType.VOITURE;  // Type de véhicule
//
//        // Créer un objet Transport sans spécifier l'ID (le repository s'en occupera)
//        Transport Updatedtransport = new Transport(0, Updatedconsumption, UpdatedstartDate, UpdatedendDate, UpdatedconsumptionType, UpdateduserId, UpdateddistanceParcourue, UpdatedtypeDeVehicule);

        TransportService transportService = new TransportService();
//        transportService.addTransport(transport);
//        transportService.updateTransport(1, Updatedtransport);
//        transportService.deleteTransport(1);
//        transportService.displayTransportById(2);

    }
}
