package services;

import models.Transport;
import repositories.TransportRepository;

import java.util.Optional;

public class TransportService {
    private final TransportRepository transportRepository;

    public TransportService() {
        this.transportRepository = new TransportRepository();
    }

    // Méthode pour ajouter un transport
    public boolean addTransport(Transport transport) {
        return transportRepository.addTransport(transport);
    }

    // Méthode pour récupérer un transport par ID
   public Optional<Transport> getTransportById(int id) {
        return transportRepository.getTransportById(id);
   }

    // Méthode pour mettre à jour un transport
    public boolean updateTransport(int id ,Transport transport) {
        return transportRepository.updateTransport(id,transport);
    }

    public boolean deleteTransport(int id) {
        return transportRepository.deleteTransport(id);
    }

    public void displayTransportById(int id) {
        Optional<Transport> transportOpt = transportRepository.getTransportById(id);

        if (transportOpt.isPresent()) {
            Transport transport = transportOpt.get();
            System.out.println("Transport ID: " + id);
            System.out.println("Consommation: " + transport.getConsumption());
            System.out.println("Date de début: " + transport.getStartDate());
            System.out.println("Date de fin: " + transport.getEndDate());
            System.out.println("Type de consommation: " + transport.getConsumptionType());
            System.out.println("ID utilisateur: " + transport.getUserId());
            System.out.println("Distance parcourue: " + transport.getDistanceParcourue());
            System.out.println("Type de véhicule: " + transport.getTypeDeVehicule());
        } else {
            System.out.println("Transport non trouvé pour l'ID: " + id);
        }
    }
}
