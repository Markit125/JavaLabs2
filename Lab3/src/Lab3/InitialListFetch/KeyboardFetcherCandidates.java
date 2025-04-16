package Lab3.InitialListFetch;

import Lab3.Candidate;

import java.util.*;
import java.text.*;


import java.util.List;

import static Lab3.InputHelpMethods.InputHelper.*;

public class KeyboardFetcherCandidates extends InitialCandidatesListFetcher {

    public KeyboardFetcherCandidates() {}

    private String getNextLine(Scanner scanner, String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public List<Candidate> getInitialList() {
        Scanner scanner = new Scanner(System.in);
        List<Candidate> candidates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int n = getNonNegativeInteger(message -> getNextLine(scanner, message), "Enter number of candidates");

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for candidate " + (i + 1) + ":");

            String firstName = getNotEmptyString(message -> getNextLine(scanner, message), "First name");
            String middleName = getNotEmptyString(message -> getNextLine(scanner, message), "Middle name");
            String lastName = getNotEmptyString(message -> getNextLine(scanner, message), "Last name");
            Date birthDate = getBirthDate(message -> getNextLine(scanner, message), dateFormat);
            String placeOfBirth = getNotEmptyString(message -> getNextLine(scanner, message), "Place of birth");
            int popularityIndex = getNonNegativeInteger(message -> getNextLine(scanner, message), "Popularity index (non-negative integer)");

            Candidate candidate = new Candidate(firstName, middleName, lastName, birthDate, placeOfBirth, popularityIndex);
            candidates.add(candidate);
        }

        return candidates;
    }
}
