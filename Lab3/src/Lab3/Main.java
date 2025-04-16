package Lab3;

import Lab3.InitialListFetch.FileFetcherCandidates;
import Lab3.InitialListFetch.InitialCandidatesListFetcher;
import Lab3.InitialListFetch.KeyboardFetcherCandidates;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String askEnterStr = "Initial candidates list:\n" +
            "1 - load from file\n" +
            "2 - enter with keyboard\n" +
            "3 - none";
        System.out.println(askEnterStr);

        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (!(1 <= option && option <= 3)) {
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("No such option. Try again.");
                System.out.println(askEnterStr);
            }
        }

        InitialCandidatesListFetcher fetcher = switch (option) {
            case 1 -> new FileFetcherCandidates("src/Lab3/initCandidates.csv");
            case 2 -> new KeyboardFetcherCandidates();
            default -> new InitialCandidatesListFetcher();
        };

        CandidatesGUI candidatesGUI = new CandidatesGUI(fetcher);
        candidatesGUI.setVisible(true);
    }
}
