import java.util.*;

public class Main {

    static final int DATA_SIZE = 1_000_000;
    static final int RANDOM_ACCESS_COUNT = 100_00;

    private static void fillArraysWithRandom(ArrayList<Integer> arrayList, LinkedList<Integer> linkedList) {
        Random rand = new Random();

        int num;
        for (int i = 0; i < DATA_SIZE; i++) {
            num = rand.nextInt(DATA_SIZE);
            arrayList.add(num);
            linkedList.add(num);
        }
    }

    private static ArrayList<Integer> getRandomList(int size) {
        ArrayList<Integer> arr = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < RANDOM_ACCESS_COUNT; i++) {
            arr.add(rand.nextInt(size));
        }

        return arr;
    }

    private static void benchmarkList(Runnable func) {
        long startTime = System.nanoTime();

        func.run();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Execution time: " + (duration / 1_000_000.0) + " ms");
    }

    private static void chooseNumbers(AbstractList<Integer> collection, ArrayList<Integer> chooseArray) {
        for (Integer integer : chooseArray) {
            collection.set(integer, -1);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        fillArraysWithRandom(arrayList, linkedList);

        ArrayList<Integer> chooseArray = getRandomList(arrayList.size());

        System.out.println("\nTest LinkedList:");
        benchmarkList(() -> chooseNumbers(linkedList, chooseArray));

        System.out.println("\nTest ArrayList:");
        benchmarkList(() -> chooseNumbers(arrayList, chooseArray));
    }
}
