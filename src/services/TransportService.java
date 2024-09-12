package services;

import models.Transport;
import repositories.TransportRepository;
import enums.ConsumptionType;
import enums.VehiculeType;

import java.time.LocalDateTime;
import java.util.Optional;

public class TransportService {
    private TransportRepository transportRepository;

    public TransportService() {
        this.transportRepository = new TransportRepository();
    }

    public boolean addTransport(double consumption, LocalDateTime startDate, LocalDateTime endDate,
                                ConsumptionType consumptionType, String userId, double distanceParcourue,
                                VehiculeType typeDeVehicule)
    {
        // Créer un objet Transport sans ID
        Transport transport = new Transport(consumption, startDate, endDate, consumptionType, userId, distanceParcourue, typeDeVehicule);
        return transportRepository.addTransport(transport);
    }

    public boolean updateTransport(int id, double newConsumption, LocalDateTime newStartDate,
                                   LocalDateTime newEndDate, ConsumptionType newConsumptionType,
                                   String newUserId, double newDistanceParcourue, VehiculeType newTypeDeVehicule) {
        Optional<Transport> optionalTransport = transportRepository.findTransportById(id);
        if (optionalTransport.isPresent()) {
            Transport transport = optionalTransport.get();
            transport.setConsumption(newConsumption);
            transport.setStartDate(newStartDate);
            transport.setEndDate(newEndDate);
            transport.setConsumptionType(newConsumptionType);
            transport.setUserId(newUserId);
            transport.setDistanceParcourue(newDistanceParcourue);
            transport.setTypeDeVehicule(newTypeDeVehicule);
            return transportRepository.updateTransport(transport);
        } else {
            System.out.println("Transport non trouvé.");
            return false;
        }
    }

    public boolean removeTransport(int id) {
        return transportRepository.deleteTransport(id);
    }

    public void displayTransport(int id) {
        Optional<Transport> optionalTransport = transportRepository.findTransportById(id);
        optionalTransport.ifPresentOrElse(
                transport -> System.out.println("ID: " + transport.getId() +
                        "\tConsumption: " + transport.getConsumption() +
                        "\tStart Date: " + transport.getStartDate() +
                        "\tEnd Date: " + transport.getEndDate() +
                        "\tType: " + transport.getConsumptionType() +
                        "\tUser ID: " + transport.getUserId() +
                        "\tDistance: " + transport.getDistanceParcourue() +
                        "\tVehicle Type: " + transport.getTypeDeVehicule()),
                () -> System.out.println("Transport non trouvé.")
        );
    }

    public boolean transportExists(int id) {
        return transportRepository.findTransportById(id).isPresent();
    }
}
