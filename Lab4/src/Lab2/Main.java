package Lab2;

public class Main {
    public static void main(String[] args) {
        int countClothes = 5;
        Clothes[] clothes = new Clothes[] {
            new Shirt(Clothes.Size.LARGE, Clothes.Color.BLACK),
            new Coat(Clothes.Size.LARGE),
            new Jeans(Clothes.Size.MEDIUM, Clothes.Color.ORANGE),
            new Shirt(Clothes.Size.MEDIUM, Clothes.Color.ORANGE),
            new Coat(Clothes.Size.MEDIUM, Clothes.Color.YELLOW, Coat.Material.FLEECE),
        };

        Wardrobe wardrobe = new Wardrobe(countClothes);
        wardrobe.setClothes(clothes);

        System.out.println(wardrobe);
        System.out.println(wardrobe.countFitClothes(Clothes.Size.LARGE, Clothes.Color.BLACK));
        System.out.println(wardrobe.countFitClothes(Clothes.Size.MEDIUM, Clothes.Color.ORANGE));

        wardrobe.searchObjects(Shirt.class);
        wardrobe.searchObjects(Jeans.class);
    }
}