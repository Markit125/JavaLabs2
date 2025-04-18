package Converter;

public class CelsiusToFarenheitConverter extends BaseConverter {
    public double convert(double celsiusDegrees) {
        return celsiusDegrees * 1.8 + 32;
    }
}
