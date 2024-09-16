import enums.AlimentType;
import enums.ConsumptionType;
import enums.EnergyType;
import enums.VehiculeType;
import models.Alimentation;
import models.Logement;
import models.Transport;
import services.AlimentationService;
import services.LogementService;
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
//        Transport transport = new Transport(consumption, startDate, endDate, consumptionType, userId, distanceParcourue, typeDeVehicule);


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

//        TransportService transportService = new TransportService();
//        transportService.addTransport(transport);
//        transportService.updateTransport(1, Updatedtransport);
//        transportService.deleteTransport(2);
//        transportService.displayTransportById(2);

//      //   Définir les données pour un transport
//        double Updateconsumption = 290;
//        LocalDateTime UpdatestartDate = LocalDateTime.of(2024, 9, 13, 10, 0);
//        LocalDateTime UpdateendDate = LocalDateTime.of(2024, 9, 13, 15, 0);
//        ConsumptionType UpdateconsumptionType = ConsumptionType.LOGEMENT;
//        String userId = "USER123";  // Exemple d'ID utilisateur
//        double Updateconsommation_energie = 900;  // Distance parcourue
//        EnergyType UpdatetypeEnergy = EnergyType.GAZ;  // Type de véhicule
////
////        // Créer un objet Transport sans spécifier l'ID (le repository s'en occupera)
//        Logement Updatelogement = new Logement(Updateconsumption, UpdatestartDate, UpdateendDate, UpdateconsumptionType, userId, Updateconsommation_energie, UpdatetypeEnergy);

//        TransportService transportService = new TransportService();
//        transportService.addTransport(transport);
//        transportService.updateTransport(1, Updatedtransport);
//        transportService.deleteTransport(5);
//        transportService.displayTransportById(2);

//       LogementService logementService = new LogementService();
//        logementService.addLogement(Updatelogement);
//        logementService.updateLogement(2, Updatelogement);
//        logementService.displayLogementById(7);


//        // Définir les données pour un alimentation
//        double consumption = 126.34;
//        LocalDateTime startDate = LocalDateTime.of(2024, 12, 12, 12, 12);
//        LocalDateTime endDate = LocalDateTime.of(2024, 12, 19, 12, 12);
//        ConsumptionType consumptionType = ConsumptionType.ALIMENTATION;
//        String userId = "USER123";  // Exemple d'ID utilisateur
//        double weight = 18.7;  // Distance parcourue
//        AlimentType alimentation_type = AlimentType.LEGUME;  // Type de véhicule
//
//        // Créer un objet Transport sans spécifier l'ID (le repository s'en occupera)
//        Alimentation alimentation = new Alimentation(consumption, startDate, endDate, consumptionType, userId, alimentation_type, weight);

//        double Updateconsumption = 12.12;
//        LocalDateTime UpdatestartDate = LocalDateTime.of(2024, 12, 12, 12, 12);
//        LocalDateTime UpdateendDate = LocalDateTime.of(2024, 12, 19, 12, 12);
//        ConsumptionType UpdateconsumptionType = ConsumptionType.ALIMENTATION;
//        String userId = "USER123";  // Exemple d'ID utilisateur
//        double Updateweight = 500;  // Distance parcourue
//        AlimentType Updatealimentation_type = AlimentType.VIANDE;  // Type de véhicule
//
//        Alimentation updateAlimentation = new Alimentation(Updateconsumption, UpdatestartDate, UpdateendDate, UpdateconsumptionType, userId, Updatealimentation_type, Updateweight);

//        AlimentationService alimentationService = new AlimentationService();
//        alimentationService.addAlimentation(alimentation);
//        alimentationService.updateAlimentation(8,updateAlimentation);
//          alimentationService.deleteAlimentation(9);
//        alimentationService.displayAlimentationById(9);
    }
}
