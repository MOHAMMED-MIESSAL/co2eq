package models;

import java.time.LocalDateTime;

import enums.ConsumptionType;
import enums.VehiculeType;

public class Transport extends Consumption {

    private int id; // Ajoutez cet attribut pour stocker l'ID
    private double distanceParcourue;
    private VehiculeType typeDeVehicule;

    public Transport(double consumption, LocalDateTime startDate, LocalDateTime endDate, ConsumptionType consumptionType, String userId, double distanceParcourue, VehiculeType typeDeVehicule) {
        super(consumption, startDate, endDate, consumptionType, userId);
        this.distanceParcourue = distanceParcourue;
        this.typeDeVehicule = typeDeVehicule;
    }

    public Transport(int id, double consumption, LocalDateTime startDate, LocalDateTime endDate, ConsumptionType consumptionType, String userId, double distanceParcourue, VehiculeType typeDeVehicule) {
        super(consumption, startDate, endDate, consumptionType, userId);
        this.id = id;
        this.distanceParcourue = distanceParcourue;
        this.typeDeVehicule = typeDeVehicule;
    }

    // Getter pour ID
    public int getId() {
        return id;
    }

    // Setter pour ID
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double calculImpact() {
        return distanceParcourue * typeDeVehicule.getImpact();
    }

    @Override
    public double getConsumption() {
        return super.getConsumption();
    }

    @Override
    public LocalDateTime getEndDate() {
        return super.getEndDate();
    }

    @Override
    public LocalDateTime getStartDate() {
        return super.getStartDate();
    }

    @Override
    public void setConsumption(double consumption) {
        super.setConsumption(consumption);
    }

    @Override
    public void setEndDate(LocalDateTime endDate) {
        super.setEndDate(endDate);
    }

    @Override
    public void setStartDate(LocalDateTime startDate) {
        super.setStartDate(startDate);
    }

    public double getDistanceParcourue() {
        return distanceParcourue;
    }

    public void setDistanceParcourue(double distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }

    public VehiculeType getTypeDeVehicule() {
        return typeDeVehicule;
    }

    public void setTypeDeVehicule(VehiculeType typeDeVehicule) {
        this.typeDeVehicule = typeDeVehicule;
    }
}
