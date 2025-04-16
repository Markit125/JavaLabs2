package Lab1;

public class Angle {
    private int degrees;
    private int minutes;

    public Angle(int degrees, int minutes) {
        this.degrees = degrees;
        this.minutes = minutes;
        normalize();
    }

    private void normalize() {
        int totalMinutes = degrees * 60 + minutes;
        totalMinutes = ((totalMinutes % (360 * 60)) + (360 * 60)) % (360 * 60);
        degrees = totalMinutes / 60;
        minutes = totalMinutes % 60;
    }

    public double toRadians() {
        return Math.toRadians(degrees + minutes / 60.0);
    }

    public void increase(int deg, int min) {
        degrees += deg;
        minutes += min;
        normalize();
    }

    public void decrease(int deg, int min) {
        degrees -= deg;
        minutes -= min;
        normalize();
    }

    public Angle add(Angle other) {
        return new Angle(this.degrees + other.degrees, this.minutes + other.minutes);
    }

    public Angle subtract(Angle other) {
        return new Angle(this.degrees - other.degrees, this.minutes - other.minutes);
    }

    public double sin() {
        return Math.sin(toRadians());
    }

    public double cos() {
        return Math.cos(toRadians());
    }

    public double tan() {
        double tan = Math.tan(toRadians());
        if (tan > 1e9) throw new ArithmeticException("Tan undefined because of cot = 0");
        return tan;
    }

    public double cot() {
        double cot = 1.0 / Math.tan(toRadians());
        if (cot > 1e9) throw new ArithmeticException("Cot undefined because of tan = 0");
        return cot;
    }

    public int getDegrees() {
        return degrees;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Angle other)) return false;
        return this.degrees == other.degrees && this.minutes == other.minutes;
    }

    @Override
    public String toString() {
        return String.format("%d° %d'", degrees, minutes);
    }

    public static void main(String[] args) {
        Angle a1 = new Angle(370, 80);
        Angle a2 = new Angle(15, 45);
        System.out.println("Угол a1: " + a1);
        System.out.println("Угол a2: " + a2);

        a1.increase(5, 20);
        System.out.println("После увеличения a1: " + a1);

        a2.decrease(10, 30);
        System.out.println("После уменьшения a2: " + a2);

        Angle sum = a1.add(a2);
        System.out.println("Сумма углов: " + sum);

        Angle diff = a1.subtract(a2);
        System.out.println("Разность углов: " + diff);

        System.out.printf("Синус a1: %.4f\n", a1.sin());
        System.out.printf("Косинус a1: %.4f\n", a1.cos());
        System.out.printf("Тангенс a1: %.4f\n", a1.tan());
        System.out.printf("Котангенс a1: %.4f\n", a1.cot());
    }
}
