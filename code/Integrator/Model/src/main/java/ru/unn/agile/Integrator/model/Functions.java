package ru.unn.agile.Integrator.model;

public class Functions {
    private final String number;
    private static final int CONSTANT1 = 10;
    private static final int CONSTANT2 = 100;

    public Functions(final String number) {
        this.number = number;
    }

    public double getResult(final double x) {
        switch (number) {
            case "1":
                return function1(x);
            case "2":
                return function2(x);
            case "3":
                return function3(x);
            default:
                throw new IllegalArgumentException("Incorrect function number");
        }
    }

    private double function1(final double x) {
        return 1 / (1 + x * x);
    }

    private double function2(final double x) {
        return 1 / (1 + x * x) + Math.cos(CONSTANT1 * x);
    }

    private double function3(final double x) {
        return 1 / (1 + x * x) + Math.cos(CONSTANT2 * x);
    }
}
