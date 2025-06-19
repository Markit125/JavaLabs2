public class ParallelOutput extends Thread {
    String output;

    public ParallelOutput(String output) {
        this.output = output;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            System.out.println(output);
        }
    }

    public static void main(String[] args) {
        ParallelOutput[] threads = {
                new ParallelOutput("Parallel output 1 "),
                new ParallelOutput("Parallel output 2 "),
                new ParallelOutput("Parallel output 3 ")
        };

        for (ParallelOutput thread : threads) {
            thread.start();
        }
    }
}
