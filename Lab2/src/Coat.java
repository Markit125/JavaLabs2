public class Coat extends Clothes {
    public enum Material {
        WOOL, FLEECE, DENIM, FUR, SILK
    }

    private Material material;

    public Coat(Size size) {
        super(size, Color.BLACK);
        this.material = Material.WOOL;
    }

    public Coat(Size size, Color color) {
        super(size, color);
        this.material = Material.WOOL;
    }

    public Coat(Size size, Color color, Material material) {
        super(size, color);
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Coat;\nSize: " + getSize() + ";\nColor: " + getColor() + ";\nMaterial: " + getMaterial();
    }
}
