import enums.AlimentType;
import enums.ConsumptionType;
import enums.EnergyType;
import enums.VehiculeType;
import models.Alimentation;
import models.Logement;
import models.Transport;
import services.LogementService;
import services.TransportService;
import services.AlimentationService;
import services.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static final String RESET = "\033[0m";
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        TransportService transportService = new TransportService();
        AlimentationService alimentationService = new AlimentationService();
        LogementService logementService = new LogementService();

        while (true) {
            System.out.println("=======================================");
            System.out.println("      Carbon Consumption Manager");
            System.out.println("=======================================");
            System.out.println("1. Create User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Add Carbon Consumption for Transport");
            System.out.println("5. Add Carbon Consumption for Alimentation");
            System.out.println("6. Add Carbon Consumption for Logement");
            System.out.println("7. Users with 3000 KgCO2eq Consumption");
            System.out.println("8. Inactive Users");
            System.out.println("9. Sort Users by Total Consumption");
            System.out.println("10. Exit");
            System.out.println("=======================================");
            System.out.print("Please choose an option (1-10): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Create User ---");
                    System.out.print("Enter username: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    userService.addUser(name, age, id);
                    System.out.println("User added successfully.\n");
                    break;
                case 2:
                    System.out.println("\n--- Update User ---");
                    System.out.print("Enter user ID to modify: ");
                    id = scanner.nextLine();
                    System.out.print("Enter new username: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new age: ");
                    age = scanner.nextInt();
                    scanner.nextLine();
                    userService.updateUser(id, name, age);
                    System.out.println("User updated successfully.\n");
                    break;
                case 3:
                    System.out.println("\n--- Delete User ---");
                    System.out.print("Enter user ID to delete: ");
                    id = scanner.nextLine();
                    userService.removeUser(id);
                    System.out.println("User deleted successfully.\n");
                    break;
                case 4:
                    addTransport(scanner, transportService);
                    break;
                case 5:
                    addAlimentation(scanner, alimentationService);
                    break;
                case 6:
                    addLogement(scanner, logementService);
                    break;
                case 7:
                    userService.displayUsersExceedingConsumptionThreshold();
                    break;
                case 8:
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    LocalDateTime startDateInac = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    LocalDateTime endDateInac = LocalDateTime.parse(scanner.nextLine() + "T23:59:59");
                    userService.displayInactiveUsers(startDateInac, endDateInac);
                    break;
                case 9:
                    userService.triUsersByTotalConsumption();
                    break;
                case 10:
                    System.out.println("Exiting... Thank you for using the Carbon Consumption Manager.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 10.");
                    break;
            }
        }
    }

    private static void addTransport(Scanner scanner, TransportService transportService) {
        System.out.println("\n--- Add Transport Carbon Consumption ---");
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter carbon consumption (kg CO2): ");
        double consumption = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine() + "T23:59:59");
        System.out.print("Enter distance parcourue (km): ");
        int distanceParcourue = scanner.nextInt();
        scanner.nextLine();

        VehiculeType typeDeVehicule = null;
        while (typeDeVehicule == null) {
            System.out.print("Enter vehicle type (VOITURE or TRAIN): ");
            String vehicleTypeInput = scanner.nextLine().toUpperCase();
            if (vehicleTypeInput.equals("VOITURE") || vehicleTypeInput.equals("TRAIN")) {
                typeDeVehicule = VehiculeType.valueOf(vehicleTypeInput);
            } else {
                System.out.println("Invalid vehicle type. Please enter 'VOITURE' or 'TRAIN'.");
            }
        }

        Transport transport = new Transport(consumption, startDate, endDate, ConsumptionType.TRANSPORT, id, distanceParcourue, typeDeVehicule);
        transportService.addTransport(transport);
        System.out.println("Transport consumption added successfully.\n");
    }

    private static void addAlimentation(Scanner scanner, AlimentationService alimentationService) {
        System.out.println("\n--- Add Alimentation Carbon Consumption ---");
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter carbon consumption (kg CO2): ");
        double consumption = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine() + "T23:59:59");
        System.out.print("Enter weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        AlimentType alimentType = null;
        while (alimentType == null) {
            System.out.print("Enter aliment type (VIANDE or LEGUME): ");
            String alimentTypeInput = scanner.nextLine().toUpperCase();
            if (alimentTypeInput.equals("VIANDE") || alimentTypeInput.equals("LEGUME")) {
                alimentType = AlimentType.valueOf(alimentTypeInput);
            } else {
                System.out.println("Invalid aliment type. Please enter 'VIANDE' or 'LEGUME'.");
            }
        }

        Alimentation alimentation = new Alimentation(consumption, startDate, endDate, ConsumptionType.ALIMENTATION, id, alimentType, weight);
        alimentationService.addAlimentation(alimentation);
        System.out.println("Alimentation consumption added successfully.\n");
    }

    private static void addLogement(Scanner scanner, LogementService logementService) {
        System.out.println("\n--- Add Logement Carbon Consumption ---");
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter carbon consumption (kg CO2): ");
        double consumption = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine() + "T23:59:59");
        System.out.print("Enter energy consumption (kWh): ");
        double energyConsumption = scanner.nextDouble();
        scanner.nextLine();

        EnergyType energyType = null;
        while (energyType == null) {
            System.out.print("Enter energy type (ELECTRICITE or GAZ): ");
            String energyTypeInput = scanner.nextLine().toUpperCase();
            if (energyTypeInput.equals("ELECTRICITE") || energyTypeInput.equals("GAZ")) {
                energyType = EnergyType.valueOf(energyTypeInput);
            } else {
                System.out.println("Invalid energy type. Please enter 'ELECTRICITE' or 'GAZ'.");
            }
        }

        Logement logement = new Logement(consumption, startDate, endDate, ConsumptionType.LOGEMENT, id, energyConsumption, energyType);
        logementService.addLogement(logement);
        System.out.println("Logement consumption added successfully.\n");
    }
}
