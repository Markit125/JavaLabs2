package Filter;

import java.util.Arrays;

public class Main {
    private static int cleanArray(Object[] array, Filter filter) {
        int writeIndex = 0;

        for (Object o : array) {
            if (filter.apply(o)) {
                array[writeIndex] = o;
                writeIndex++;
            }
        }

        for (int i = writeIndex; i < array.length; i++) {
            array[i] = null;
        }

        return writeIndex;
    }

    public static void main(String[] args) {
        Object[] array1 = new Object[] {
                "abc", 1, 1.4, new StringBuffer(), (float) 34, new NumbersApplier(), (byte) 8, new FilterApplier(), 'b'
        };

        Object[] array2 = array1.clone();

        int lenArray1 = cleanArray(array1, new FilterApplier());
        int lenArray2 = cleanArray(array2, new NumbersApplier());

        System.out.println("Array with only filters:");
        System.out.println(Arrays.toString(Arrays.copyOfRange(array1, 0, lenArray1)));

        System.out.println("\nArray with only numbers:");
        System.out.println(Arrays.toString(Arrays.copyOfRange(array2, 0, lenArray2)));
    }
}
