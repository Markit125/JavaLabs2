import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Math.max;

public class MaxPrimeFinder {
    private boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int MaxPrime(int begin, int end) {
        final int threadsCount = Runtime.getRuntime().availableProcessors() - 1;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Future<Integer>> futures = new ArrayList<>();

        int segmentSize = (end - begin) / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            final int segmentBegin = i * segmentSize + begin;
            final int segmentEnd = (i == threadsCount - 1) ? end : (i + 1) * segmentSize + begin;

            Callable<Integer> findMaxPrimeTask = () -> {
                int maxPrime = -1;
                for (int j = segmentBegin; j < segmentEnd; j++) {
                    if (isPrime(j)) {
                        maxPrime = max(maxPrime, j);
                    }
                }
                return maxPrime;
            };

            futures.add(executorService.submit(findMaxPrimeTask));
        }

        int maxPrime = -1;

        for (Future<Integer> future : futures) {
            try {
                int result = future.get();
                maxPrime = max(maxPrime, result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                System.err.println("ExecutorService tasks lasts too long. Force shutdown.");
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted while waiting for executor termination.");
        }

        return maxPrime;
    }

    public static void main(String[] args) {
        MaxPrimeFinder maxPrimeFinder = new MaxPrimeFinder();
        System.out.println("Max Prime number from [20; 22] is: " + maxPrimeFinder.MaxPrime(20, 23));
        System.out.println("Max Prime number from [0; 1024] is: " + maxPrimeFinder.MaxPrime(0, 1025));
        System.out.println("Max Prime number from [0; 1234567] is: " + maxPrimeFinder.MaxPrime(0, 1234568));
    }
}
