package enums;

public enum EnergyType {
    ELECTRICITE(1.5),
    GAZ(2.0);

    private final double impact;

    EnergyType(double impact) {
        this.impact = impact;
    }

    public double getImpact() {
        return impact;
    }
}
