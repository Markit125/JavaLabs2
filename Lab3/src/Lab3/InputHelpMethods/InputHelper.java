package Lab3.InputHelpMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class InputHelper {
    public static String getNotEmptyString(Function<String, String> stringGetter, String strName) {
        String str = stringGetter.apply(strName + ":");
        if (str == null) throw new NullPointerException(strName + " is null");
        while (str.isEmpty()) {
            str = stringGetter.apply(strName + " should not be empty. Try again.\n" + strName + ":");
            if (str == null) throw new NullPointerException(strName + " is null");
        }

        return str;
    }

    public static Date getBirthDate(Function<String, String> dateGetter, SimpleDateFormat dateFormat) {
        String strDate = dateGetter.apply("Birth date (yyyy-MM-dd):");
        Date date = null;

        while (date == null) {
            if (strDate == null) throw new NullPointerException(strDate + " is null");
            try {
                date = dateFormat.parse(strDate);
            } catch (ParseException e) {
                strDate = dateGetter.apply("Invalid date format. Try again.\nBirth date (yyyy-MM-dd):");
            }
        }

        return date;
    }

    public static int getNonNegativeInteger(Function<String, String> intGetter, String intName) {
        int number;
        String strNum = intGetter.apply(intName + ":");

        while (true) {
            if (strNum == null) throw new NullPointerException(intName + " is null");
            try {
                number = Integer.parseInt(strNum);
            } catch (NumberFormatException e) {
                strNum = intGetter.apply("Expected non-negative integer value. Try again.\n" + intName + ":");
                continue;
            }

            if (number <= 0) {
                strNum = intGetter.apply("Integer must be non-negative. Try again.");
                continue;
            }

            return number;
        }
    }
}
