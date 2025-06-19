import java.util.Random;

public class MaxElementInMatrixFinder {
    private class rowMaxFinder extends Thread {
        private int[] row;
        private int max;

        public rowMaxFinder(int[] row) {
            this.row = row;
            this.max = Integer.MIN_VALUE;
        }

        @Override
        public void run() {
            for (int j : row) {
                if (j > max) {
                    max = j;
                }
            }
        }

        public int getMax() {
            return max;
        }
    }

    private final int [][]matrix;
    private int max;

    public MaxElementInMatrixFinder(int[][] matrix) {
        this.matrix = matrix;
        this.max = Integer.MIN_VALUE;
    }

    public int findMax() {
        rowMaxFinder[] rowMaxFinders = new rowMaxFinder[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            rowMaxFinders[i] = new rowMaxFinder(matrix[i]);
            rowMaxFinders[i].start();
        }

        for (int i = 0; i < rowMaxFinders.length; i++) {
            try {
                rowMaxFinders[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            max = Math.max(max, rowMaxFinders[i].getMax());
        }

        return max;
    }

    private static int[][] generateRandomMatrix(int rows, int cols, int min, int max) {
        int[][] matrix = new int[rows][cols];
        Random rand = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(max - min) + min;
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        int[][] matrix = generateRandomMatrix(5, 5, -20, 50);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        MaxElementInMatrixFinder finder = new MaxElementInMatrixFinder(matrix);
        System.out.println(finder.findMax());
    }
}
