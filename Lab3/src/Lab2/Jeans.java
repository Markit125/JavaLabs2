package Lab2;

public class Jeans extends Clothes {
    public enum FitType {
        SKINNY, SLIM, REGULAR, RELAXED, LOOSE
    }

    private FitType fitType;

    public Jeans(Size size) {
        super(size, Color.BLUE);
        fitType = FitType.REGULAR;
    }

    public Jeans(Size size, Color color) {
        super(size, color);
        fitType = FitType.REGULAR;
    }

    public Jeans(Size size, Color color, FitType fitType) {
        super(size, color);
        this.fitType = fitType;
    }

    public FitType getFitType() {
        return fitType;
    }

    public void setFitType(FitType fitType) {
        this.fitType = fitType;
    }

    @Override
    public String toString() {
        return "Jeans;\nSize: " + getSize() + ";\nColor: " + getColor() + ";\nFitType: " + getFitType();
    }
}
