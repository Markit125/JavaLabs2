public class ParallelArraySorter extends Thread {
    Comparable[] array;
    int left;
    int right;

    public ParallelArraySorter(Comparable[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    public void run() {
        if (left >= right) return;

        Comparable pivot = array[left + (right - left) / 2];
        int i = left;
        int j = right;

        while (i <= j) {
            while (array[i].compareTo(pivot) < 0) i++;
            while (array[j].compareTo(pivot) > 0) j--;

            if (i <= j) {
                Comparable temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        Thread leftSorter = null;
        Thread rightSorter = null;

        if (left < j) {
            leftSorter = new ParallelArraySorter(array, left, j);
            leftSorter.start();
        }

        if (i < right) {
            rightSorter = new ParallelArraySorter(array, i, right);
            rightSorter.start();
        }

        try {
            if (leftSorter != null) leftSorter.join();
            if (rightSorter != null) rightSorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {5, 2, 9, 1, 3, 6, 7, 8, 0, 4};

        ParallelArraySorter sorter = new ParallelArraySorter(arr, 0, arr.length - 1);
        sorter.start();

        try {
            sorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Comparable num : arr) {
            System.out.print(num + " ");
        }
    }
}
