public class Shirt extends Clothes {
    public enum SleeveLength {
        SHORT, THREE_QUARTER, LONG
    }

    private SleeveLength sleeveLength;

    public Shirt(Size size) {
        super(size, Color.WHITE);
        this.sleeveLength = SleeveLength.SHORT;
    }

    public Shirt(Size size, Color color) {
        super(size, color);
        this.sleeveLength = SleeveLength.SHORT;
    }

    public Shirt(Size size, Color color, SleeveLength sleeveLength) {
        super(size, color);
        this.sleeveLength = sleeveLength;
    }

    public SleeveLength getSleeveLength() {
        return sleeveLength;
    }

    public void setSleeveLength(SleeveLength sleeveLength) {
        this.sleeveLength = sleeveLength;
    }

    @Override
    public String toString() {
        return "Shirt;\nSize: " + getSize() + ";\nColor: " + getColor() + ";\nSleeveLength: " + getSleeveLength();
    }
}
