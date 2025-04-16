public class Clothes {
    public enum Color {
        BLACK, WHITE, BLUE, RED, GREEN, YELLOW, ORANGE, PURPLE
    }

    public enum Size {
        XSMALL, SMALL, MEDIUM, LARGE, XLARGE
    }

    private Size size;
    private Color color;

    Clothes(Size size, Color color) {
        this.size = size;
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String toString() {
        return "Clothe;\nsize=" + size + ";\ncolor=" + color + "\n";
    }
}
