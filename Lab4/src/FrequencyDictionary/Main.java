package FrequencyDictionary;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        FrequencyDictionary frequencyDictionary = null;

        try (BufferedReader br = new BufferedReader(new FileReader("src/FrequencyDictionary/text.txt"))) {
            frequencyDictionary = new FrequencyDictionary(br);
            frequencyDictionary.analyze();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        String[] wordsToFind = {
                "Java", "and", "java", "is", "program", "", "cause"
        };

        for (String word : wordsToFind) {
            System.out.println(word + ": " + frequencyDictionary.getFrequency(word));
        }
    }
}
