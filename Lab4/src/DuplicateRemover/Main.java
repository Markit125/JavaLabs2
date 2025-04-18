package DuplicateRemover;

import java.util.*;

public class Main {
    private static Collection<Object> duplicateRemover(Collection<Object> collection) {
        Set<Object> objects = new HashSet<>();
        Collection<Object> collectionsWithoutDuplicates = new ArrayList<>();

        for (Object obj : collection) {
            if (!objects.contains(obj)) {
                objects.add(obj);
                collectionsWithoutDuplicates.add(obj);
            }
        }

        return collectionsWithoutDuplicates;
    }

    public static void main(String[] args) {

        Scanner sc1 = new Scanner(System.in);
        Scanner sc2;
        sc2 = sc1;

        Collection<Object> collection = new ArrayList<>(List.of(
                sc1, sc2, "aaa", "bbb", "aaa", 125, 125, new NullPointerException("err"), new NullPointerException("err")
        ));

        Collection<Object> collectionWithoutDuplicates = duplicateRemover(collection);

        System.out.println("Collection without duplicates:");
        for (Object obj : collectionWithoutDuplicates) {
            System.out.println(obj);
        }
    }
}
