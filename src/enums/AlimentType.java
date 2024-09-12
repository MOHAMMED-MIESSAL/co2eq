package enums;

public enum AlimentType {
    VIANDE(5.0),
    LEGUME(0.5);

    private final double impact;

    AlimentType(double impact) {
        this.impact = impact;
    }

    public double getImpact() {
        return impact;
    }
}
