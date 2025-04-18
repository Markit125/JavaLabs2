package Converter;

public class Main {
    public static void main(String[] args) {
        BaseConverter converterToKelvin = new CelsiusToKelvinConverter();
        BaseConverter converterToFahrenheit = new CelsiusToFarenheitConverter();

        System.out.println("32 degrees Celsius in Kelvin is " + converterToKelvin.convert(32));
        System.out.println("-40 degrees Celsius in Farenhait: " + converterToFahrenheit.convert(-40));
    }
}
