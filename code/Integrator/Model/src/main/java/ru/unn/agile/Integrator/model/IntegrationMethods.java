package ru.unn.agile.Integrator.model;

public class IntegrationMethods {
    private final int n;
    private final double a;
    private final double b;

    public IntegrationMethods(final int n, final double a, final double b) {
        this.n = n;
        this.a = a;
        this.b = b;
    }

    public double rectangle(final String functionType) {
        Functions func = new Functions(functionType);
        double res = 0, h = (b - a) / n;

        for (int i = 0; i < n; i++) {
            res += (func.getResult((2 * a + 2 * i * h + h) / 2));
        }
        res *= h;
        return res;
    }

    public double trapezoid(final String functionType) {
        Functions func = new Functions(functionType);
        double res = 0, h = (b - a) / n;

        for (int i = 1; i < n; i++) {
            res += func.getResult(i * h);
        }
        res += (func.getResult(a) + func.getResult(b)) / 2;
        res *= h;

        return res;
    }

    public double simpson(final String functionType) {
        Functions func = new Functions(functionType);

        double res = 0;
        final int constant1 = 2;
        final int constant2 = 4;
        final int constant3 = 6;

        int n = this.n / 2;

        for (int i = 1; i < 2 * n; i += 2) {
            res += constant2 * func.getResult(a + ((b - a) * i / (2 * n)));
        }
        for (int i = 2; i < 2 * n - 1; i += 2) {
            res += constant1 * func.getResult(a + ((b - a) * i / (2 * n)));
        }

        res += func.getResult(a) + func.getResult(b);
        res *= (b - a) / (constant3 * n);

        return res;
    }
}
