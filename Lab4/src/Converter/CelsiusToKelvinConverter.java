package Converter;

public class CelsiusToKelvinConverter extends BaseConverter {
    @Override
    public double convert(double celsiusDegrees) {
        return celsiusDegrees + 273;
    }
}
