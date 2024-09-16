package models;

import java.time.LocalDateTime;

import enums.ConsumptionType;
import enums.VehiculeType;

public class Transport extends Consumption {

    private int id;
    private int distanceParcourue;
    private VehiculeType typeDeVehicule;

    public Transport(double consumption, LocalDateTime startDate, LocalDateTime endDate, ConsumptionType consumptionType, String userId, int distanceParcourue, VehiculeType typeDeVehicule) {
        super(consumption, startDate, endDate, consumptionType, userId);
//        this.id = id;
        this.distanceParcourue = distanceParcourue;
        this.typeDeVehicule = typeDeVehicule;
    }


    // Getters & Setters :
    public double getDistanceParcourue() {
        return distanceParcourue;
    }
    public void setDistanceParcourue(int distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public VehiculeType getTypeDeVehicule() {
        return typeDeVehicule;
    }
    public void setTypeDeVehicule(VehiculeType typeDeVehicule) {
        this.typeDeVehicule = typeDeVehicule;
    }
    //

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


}
