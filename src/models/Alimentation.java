package models;

import enums.AlimentType;
import enums.ConsumptionType;

import java.time.LocalDateTime;

public class Alimentation extends Consumption {
    int id;
    private double weight;
    private AlimentType alimentType;

    public Alimentation(double consumption, LocalDateTime startDate, LocalDateTime endDate, ConsumptionType consumptionType, String userId, AlimentType alimentType, double weight) {
        super(consumption, startDate, endDate, consumptionType, userId);
        this.alimentType = alimentType;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public AlimentType getAlimentType() {
        return alimentType;
    }
    public void setAlimentType(AlimentType alimentType) {
        this.alimentType = alimentType;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }




    @Override
    public double calculImpact() {
        return weight * alimentType.getImpact();
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
