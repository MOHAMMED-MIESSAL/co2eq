package models;

import java.time.LocalDateTime;

import enums.ConsumptionType;

public abstract class Consumption {
    private double consumption;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ConsumptionType consumptionType;
    private String userId;

    public Consumption(double consumption, LocalDateTime startDate, LocalDateTime endDate, ConsumptionType consumptionType, String userId) {
        // Validation basique de la consommation et des dates
        if (consumption < 0) {
            throw new IllegalArgumentException("La consommation ne peut pas être négative.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        this.consumption = consumption;
        this.startDate = startDate;
        this.endDate = endDate;
        this.consumptionType = consumptionType;
        this.userId = userId;
    }

    // Méthode abstraite pour calculer l'impact
    public abstract double calculImpact();

    // Getters et Setters

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getConsumption() {
        return consumption;
    }
    public void setConsumption(double consumption) {
        if (consumption < 0) {
            throw new IllegalArgumentException("La consommation ne peut pas être négative.");
        }
        this.consumption = consumption;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        if (this.startDate != null && this.startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début.");
        }
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        if (this.endDate != null && startDate.isAfter(this.endDate)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }
        this.startDate = startDate;
    }

    public ConsumptionType getConsumptionType() {
        return consumptionType;
    }
    public void setConsumptionType(ConsumptionType consumptionType) {
        this.consumptionType = consumptionType;
    }
}
