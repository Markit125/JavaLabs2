package FrequencyDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class FrequencyDictionary {
    private final Map<String, Integer> wordFrequency;
    private final BufferedReader reader;

    public FrequencyDictionary(BufferedReader reader) {
        wordFrequency = new HashMap<>();
        this.reader = reader;
    }

    public void analyze() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {

            char ch;
            int wordStart = 0;
            for (int i = 0; i < line.length(); i++) {
                ch = line.charAt(i);

                if (Character.isLetter(ch) && i < line.length() - 1) {
                    continue;
                }

                if (Character.isLetter(ch)) {
                    i++;
                }

                if (i != wordStart) {
                    wordFrequency.merge(line.substring(wordStart, i), 1, Integer::sum);
                }

                wordStart = i + 1;
            }
        }
    }

    private void addWord(String word) {
        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
    }

    public int getFrequency(String word) {
        return wordFrequency.getOrDefault(word, 0);
    }
}
