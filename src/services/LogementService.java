package services;

import models.Logement;
import repositories.LogementRepository;

import java.util.Optional;


public class LogementService {
    private final LogementRepository logementRepository;

    public LogementService() {
        this.logementRepository = new LogementRepository();
    }

    // Méthode pour ajouter un logement
    public boolean addLogement(Logement logement) {
        return logementRepository.addLogement(logement);
    }

    // Méthode pour récupérer un logement par ID
    public Optional<Logement> getLogementById(int id) {
        return logementRepository.getLogementById(id);
    }

    // Méthode pour mettre à jour un logement
    public boolean updateLogement(int id, Logement logement) {
        return logementRepository.updateLogement(id, logement);
    }

    // Methode pour supprimer un logement
    public boolean deleteLogement(int id) {
        return logementRepository.deleteLogement(id);
    }

    // Display Logement by ID
    public void displayLogementById(int id) {
        Optional<Logement> logementOpt = logementRepository.getLogementById(id);

        if (logementOpt.isPresent()) {
            Logement logement = logementOpt.get();
            System.out.println("Logement ID: " + id);
            System.out.println("Consommation: " + logement.getConsumption());
            System.out.println("Date de début: " + logement.getStartDate());
            System.out.println("Date de fin: " + logement.getEndDate());
            System.out.println("Type de consommation: " + logement.getConsumptionType());
            System.out.println("ID utilisateur: " + logement.getUserId());
            System.out.println("Consommation energie: " + logement.getEnergyConsumption());
            System.out.println("Type de consommation: " + logement.getConsumptionType());
        } else {
            System.out.println("Transport non trouvé pour l'ID: " + id);
        }
    }
}
