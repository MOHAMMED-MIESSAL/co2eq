package services;

import models.Alimentation;
import models.Logement;
import repositories.AlimentationRepository;

import java.util.Optional;

public class AlimentationService {
    private final AlimentationRepository alimentationRepository;

    public AlimentationService() {
        this.alimentationRepository = new AlimentationRepository();
    }

    // Méthode pour ajouter un alimentation
    public boolean addAlimentation(Alimentation alimentation) {
        return alimentationRepository.addAlimentation(alimentation);
    }

    // Méthode pour mettre à jour un alimentation
    public boolean updateAlimentation(int id, Alimentation alimentation) {
        return alimentationRepository.updateAlimentation(id, alimentation);
    }

    // Methode pour supprimer un alimentation
    public boolean deleteAlimentation(int id) {
        return alimentationRepository.deleteAlimentation(id);
    }

    // Display Alimentation by ID
    public void displayAlimentationById(int id) {
        Optional<Alimentation> AlimentationOpt = alimentationRepository.getAlimentationById(id);

        if (AlimentationOpt.isPresent()) {
            Alimentation alimentation = AlimentationOpt.get();
            System.out.println("Alimentation ID: " + id);
            System.out.println("Consommation: " + alimentation.getConsumption());
            System.out.println("Date de début: " + alimentation.getStartDate());
            System.out.println("Date de fin: " + alimentation.getEndDate());
            System.out.println("Type de consommation: " + alimentation.getConsumptionType());
            System.out.println("ID utilisateur: " + alimentation.getUserId());
            System.out.println("Weight: " + alimentation.getWeight());
            System.out.println("Type d'alimentation: " + alimentation.getAlimentType().name());
        } else {
            System.out.println("Alimentation non trouvé pour l'ID: " + id);
        }
    }


}
