import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WordFinder {
    public int FindWord(String text, String target) {
        final int threadsCount = Runtime.getRuntime().availableProcessors() - 1;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        List<Future<Integer>> futures = new ArrayList<>();

        int segmentSize = text.length() / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            final int segmentBegin = i * segmentSize;
            final int segmentEnd = (i == threadsCount - 1) ? text.length() : (i + 1) * segmentSize;

            Callable<Integer> findWord = () -> {
                for (int j = segmentBegin; j < segmentEnd; j++) {
                    if (text.charAt(j) == target.charAt(0)) {
                        boolean found = true;

                        for (int k = j; k < text.length() && k - j < target.length(); k++) {
                            if (text.charAt(k) != target.charAt(k - j)) {
                                found = false;
                                break;
                            }
                        }

                        if (found) {
                            return j;
                        }
                    }
                }

                return -1;
            };

            futures.add(executorService.submit(findWord));
        }

        int position = 0;

        for (Future<Integer> future : futures) {
            try {
                int result = future.get();
                if (result != -1) {
                    position = result;
                    break;
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdownNow();

        return position;
    }

    public static void main(String[] args) {
        WordFinder wordFinder = new WordFinder();

        String baseText = "Java multithreading is powerful for parallel processing. " +
                "Finding words in large text efficiently can be done in parallel. " +
                "This example demonstrates parallel word search. " +
                "The goal is to show parallel execution. " +
                "Parallel computing is an exciting field. " +
                "Search for the word 'parallel' in this text. ";

        String text = baseText.repeat(50000);
        String targetWord = "parallel";
        System.out.println("Position of word " + targetWord + " is: " + wordFinder.FindWord(text, targetWord));
        System.out.println("Position with indexOf: " + text.indexOf(targetWord));

        targetWord = "computing";
        System.out.println("Position of word " + targetWord + " is: " + wordFinder.FindWord(text, targetWord));
        System.out.println("Position with indexOf: " + text.indexOf(targetWord));

        targetWord = "target";
        System.out.println();
        text = baseText.repeat(5000000) + targetWord;

        System.out.println("Position of word " + targetWord + " is: " + wordFinder.FindWord(text, targetWord));
        System.out.println("Position with indexOf: " + text.indexOf(targetWord));
    }
}
