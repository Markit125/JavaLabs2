package Lab3.InitialListFetch;

import Lab3.Candidate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileFetcherCandidates extends InitialCandidatesListFetcher {
    String path;

    public FileFetcherCandidates(String path) {
        this.path = path;
    }

    @Override
    public List<Candidate> getInitialList() {
        List<Candidate> candidates = new ArrayList<>();
        String line;
        String csvSplitBy = ",";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSplitBy);

                String firstName = fields[0];
                String middleName = fields[1];
                String lastName = fields[2];
                Date birthDate = dateFormat.parse(fields[3]);
                String placeOfBirth = fields[4];
                int popularityIndex = Integer.parseInt(fields[5]);

                Candidate candidate = new Candidate(firstName, middleName, lastName, birthDate, placeOfBirth, popularityIndex);
                candidates.add(candidate);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return candidates;
    }
}
