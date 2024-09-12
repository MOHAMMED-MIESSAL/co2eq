package enums;

public enum VehiculeType {
    VOITURE(0.5),
    TRAIN(0.1);

    private final double impact;

    VehiculeType(double impact) {
        this.impact = impact;
    }

    public double getImpact() {
        return impact;
    }
}
