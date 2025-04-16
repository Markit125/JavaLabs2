package Lab3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Candidate {
    String firstName;
    String middleName;
    String lastName;
    Date birthDate;
    String placeOfBirth;
    int popularityIndex;

    public Candidate(String firstName, String middleName, String lastName, Date birthDate, String placeOfBirth, int popularityIndex) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.placeOfBirth = placeOfBirth;
        this.popularityIndex = popularityIndex;
    }

    public String toString() {
        return "Candidate:\nName:           " + lastName + " " + firstName.charAt(0) + "." + middleName.charAt(0) + ".\n" +
                "Birth date:     " + new SimpleDateFormat("dd.MM.yyyy").format(birthDate) + "\nPlace of birth: " + placeOfBirth + "\nPopularity:     " + popularityIndex;
    }
}
