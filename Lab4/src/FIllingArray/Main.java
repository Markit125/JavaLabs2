package FIllingArray;

import java.util.Arrays;
import java.util.function.Function;

public class Main {
    public static void fill(Integer[] array, Function<Integer, Integer> func) {
        for (int i = 0; i < array.length; i++) {
            array[i] = func.apply(i);
        }
    }

    public static void main(String[] args) {
        Integer[] squares = new Integer[100];
        fill(squares, integer -> integer * integer);

        Integer[] rounds = new Integer[100];
        fill(rounds, integer -> {
            if (integer % 10 >= 5) {
                return integer + (10 - integer % 10);
            }
            return integer - integer % 10;
        });

        System.out.println("Array of squared indices:");
        System.out.println(Arrays.toString(squares));

        System.out.println("\nArrays of round numbers:");
        System.out.println(Arrays.toString(rounds));
    }
}
