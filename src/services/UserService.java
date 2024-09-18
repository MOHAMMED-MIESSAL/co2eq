package services;

import models.Consumption;
import models.User;
import repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public boolean addUser(String name, int age, String id) {
        User user = new User(name, age, id);
        return userRepository.addUser(user);
    }

    public boolean updateUser(String id, String newName, int newAge) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(newName);
            user.setAge(newAge);
            return userRepository.updateUser(user);
        } else {
            System.out.println("Utilisateur non trouvé.");
            return false;
        }
    }

    public boolean removeUser(String id) {
        return userRepository.deleteUser(id);
    }

    public void displayUser(String id) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        optionalUser.ifPresentOrElse(
                user -> System.out.println("ID: " + user.getId() + "\tName: " + user.getName() + "\tAge: " + user.getAge()),
                () -> System.out.println("Utilisateur non trouvé.")
        );
    }

    public boolean userExists(String id) {
        return userRepository.findUserById(id).isPresent();
    }

    public void displayUsersExceedingConsumptionThreshold() {
        List<User> users = userRepository.getUsersWithTotalConsumption();

        // Filtrer les utilisateurs dont la consommation dépasse 3000 KgCO2eq
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getTotalConsumption() > 3000)
                .collect(Collectors.toList());

        // Afficher les utilisateurs filtrés
        if (filteredUsers.isEmpty()) {
            System.out.println("Aucun utilisateur avec une consommation supérieure à 3000 KgCO2eq.");
        } else {
            System.out.println("Utilisateurs avec une consommation supérieure à 3000 KgCO2eq :");
            for (User user : filteredUsers) {
                System.out.println("Nom: " + user.getName() +
                        ", ID: " + user.getId() +
                        ", Consommation totale: " + user.getTotalConsumption() + " KgCO2eq");
            }
        }
    }

    // Méthode pour calculer et afficher la consommation journalière moyenne pour un utilisateur donné
    public void calculateAndDisplayDailyAverageConsumption(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        // Récupérer les consommations de l'utilisateur entre les deux dates
        List<Consumption> consumptions = userRepository.getUserConsumptionsBetweenDates(userId, startDate, endDate);

        // Si aucune consommation n'est trouvée
        if (consumptions.isEmpty()) {
            System.out.println("Aucune consommation trouvée pour l'utilisateur avec ID : " + userId + " entre " +
                    startDate + " et " + endDate);
            return;
        }

        // Calculer le nombre de jours entre les deux dates
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Inclure le dernier jour

        // Vérifier que les jours sont valides
        if (totalDays <= 0) {
            System.out.println("Les dates fournies ne sont pas valides.");
            return;
        }

        // Calculer la consommation totale pour la période
        double totalConsumption = consumptions.stream()
                .mapToDouble(Consumption::getConsumption)
                .sum();

        // Calculer la consommation journalière moyenne
        double dailyAverageConsumption = totalConsumption / totalDays;

        // Afficher les informations
        System.out.println("Utilisateur ID : " + userId);
        System.out.println("Période de consommation : Du " + startDate + " au " + endDate);
        System.out.println("Consommation totale : " + totalConsumption + " KgCO2eq");
        System.out.println("Nombre total de jours : " + totalDays);
        System.out.println("Consommation journalière moyenne : " + dailyAverageConsumption + " KgCO2eq/jour");
    }

    // Inactif users
    public void displayInactiveUsers(LocalDateTime startDate, LocalDateTime endDate) {
        List<User> inactiveUsers = userRepository.getInactiveUsers(startDate, endDate);

        if (inactiveUsers.isEmpty()) {
            System.out.println("Aucun utilisateur inactif trouvé pour la période du " + startDate + " au " + endDate + ".");
        } else {
            System.out.println("Utilisateurs inactifs pendant la période du " + startDate + " au " + endDate + " :");
            for (User user : inactiveUsers) {
                System.out.println("Nom: " + user.getName() + ", Âge: " + user.getAge() + ", ID: " + user.getId());
            }
        }
    }

    // TRI Users
    public void triUsersByTotalConsumption() {
        // Récupérer les utilisateurs avec leur consommation totale
        List<User> sortedUsers = userRepository.sortUsersByTotalConsumption();  // Appel à la méthode dans UserRepository

        // Afficher les utilisateurs triés
        if (sortedUsers.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            System.out.println("Utilisateurs triés par consommation totale de carbone (du plus élevé au plus faible) :");
            for (User user : sortedUsers) {
                System.out.println("Nom: " + user.getName() + ", Âge: " + user.getAge() +
                        ", ID: " + user.getId() +
                        ", Consommation totale: " + user.getTotalConsumption() + " KgCO2eq");
            }
        }
    }



    // Méthode pour calculer la consommation journalière moyenne pour un utilisateur donné
//    public double calculateDailyAverageConsumption(String userId, LocalDateTime startDate, LocalDateTime endDate) {
//        // Récupérer les consommations de l'utilisateur entre les deux dates
//        List<Consumption> consumptions = userRepository.getUserConsumptionsBetweenDates(userId, startDate, endDate);
//
//        // Si aucune consommation n'est trouvée
//        if (consumptions.isEmpty()) {
//            return 0; // Aucune consommation trouvée pour cette période
//        }
//
//        // Calculer le nombre de jours
//        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Inclure le dernier jour
//
//        // Si la date de début est après la date de fin ou si aucune journée n'est comptée
//        if (totalDays <= 0) {
//            return 0; // Dates invalides ou période incorrecte
//        }
//
//        // Calculer la consommation totale pour la période
//        double totalConsumption = consumptions.stream()
//                .mapToDouble(Consumption::getConsumption)
//                .sum();
//
//        // Retourner la consommation moyenne par jour
//        return totalConsumption / totalDays;
//    }
//
    // Méthode pour afficher la consommation journalière moyenne pour tous les utilisateurs

//    public void displayDailyAverageConsumptionForUsers(List<User> users, LocalDateTime startDate, LocalDateTime endDate) {
//        for (User user : users) {
//            double dailyAverageConsumption = calculateDailyAverageConsumption(user.getId(), startDate, endDate);
//            System.out.println("Nom: " + user.getName() + ", Consommation journalière moyenne: "
//                    + dailyAverageConsumption + " KgCO2eq par jour");
//        }
//    }

//    // Méthode pour afficher la consommation journalière moyenne pour tous les utilisateurs
//    public void displayDailyAverageConsumptionForAllUsers(LocalDateTime startDate, LocalDateTime endDate) {
//        List<User> users = userRepository.getAllUsers();
//        displayDailyAverageConsumptionForUsers(users, startDate, endDate);
//    }

}
