package models;

import java.time.LocalDateTime;

import enums.ConsumptionType;
import enums.EnergyType;

public class Logement extends Consumption {

    int id;
    private EnergyType energyType;
    private double energyConsumption;

    public Logement(double consumption, LocalDateTime startDate, LocalDateTime endDate, ConsumptionType consumptionType, String userId, double energyConsumption, EnergyType energyType) {
        super(consumption, startDate, endDate, consumptionType, userId);
//        this.id = id;
        this.energyConsumption = energyConsumption;
        this.energyType = energyType;
    }

    // Getters & Setters :
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }
    public void setEnergyConsumption(double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }
    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }
    //

    @Override
    public double calculImpact() {
        return energyConsumption * energyType.getImpact();
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
