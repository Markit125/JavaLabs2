package Lab2;

public class Wardrobe {
    private int size = 3;
    private Clothes[] clothes;

    public Wardrobe() {
        clothes = new Clothes[size];
    }

    public Wardrobe(int size) {
        this.size = size;
        clothes = new Clothes[size];
    }

    public Wardrobe(int size, Clothes[] clothes) {
          this.size = size;
          this.clothes = clothes;
    }

    public Clothes[] getClothes() {
        return clothes;
    }

    public void setClothes(Clothes[] clothes) {
        this.clothes = clothes;
        this.size = clothes.length;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder wardrobe = new StringBuilder("Wardrobe:\n");
        for (Clothes clothe : clothes) {
            if (clothe != null) {
                wardrobe.append(clothe).append("\n\n");
            }
        }

        return wardrobe.toString();
    }

    public int countFitClothes(Clothes.Size size, Clothes.Color color) {
        int count = 0;
        for (Clothes clothe : clothes) {
            if (clothe != null) {
                if (clothe.getSize() == size && clothe.getColor() == color) {
                    count++;
                }
            }
        }

        return count;
    }

    public void searchObjects(Class<?> type) {
        System.out.println("Objects in wardrobe of " + type + " type:");
        for (Object obj : clothes) {
            if (type.isInstance(obj)) {
                System.out.println(obj);
            }
        }
    }
}
