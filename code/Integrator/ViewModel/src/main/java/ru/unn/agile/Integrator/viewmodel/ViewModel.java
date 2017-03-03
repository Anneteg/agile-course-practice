package ru.unn.agile.Integrator.viewmodel;

import ru.unn.agile.Integrator.model.IntegrationMethods;

import java.util.List;

public class ViewModel {
    private String a;
    private String b;
    private String n;
    private Method method;
    private Function function;
    private String result;
    private String error;
    private String status;
    private boolean isCalculateButtonEnabled;
    private boolean isInputChanged;
    private ILogger logger;

    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;
        a = "";
        b = "";
        n = "";
        method = Method.Rectangle;
        function = Function.function1;
        status = Status.WAITING;
        error = "";
        result = "";
        isCalculateButtonEnabled = false;
        isInputChanged = true;
    }

    private void logInputParams() {
        if (!isInputChanged) {
            return;
        }

        logger.log(editingFinishedLogMessage());
        isInputChanged = false;
    }

    public void focusLost() {
        logInputParams();
    }

    private String editingFinishedLogMessage() {

        return LogMessages.EDITING_FINISHED
                + "Входящие аргументы: ["
                + a + "; "
                + b + "; "
                + n + "]";
    }

    public void processKeyInTextField(final int keyCode) {
        parseInput();
        if (keyCode == KeyboardKeys.ENTER) {
            enterPressed();
        }
    }

    private void enterPressed() {
        logInputParams();

        if (isCalculateButtonEnabled()) {
            calculate();
        }
    }

    public boolean isCalculateButtonEnabled() {
        return isCalculateButtonEnabled;
    }

    private boolean isInputAvailable() {
        return !isEmpty(a) && !isEmpty(b) && !isEmpty(n);
    }

    private boolean parseInput() {
        try {
            if (!isEmpty(a)) {
                Double.parseDouble(a);
            }
            if (!isEmpty(b)) {
                Double.parseDouble(b);
            }
            if (!isEmpty(n)) {
                Integer.parseInt(n);
            }
        } catch (Exception e) {
            status = Status.BAD_FORMAT;
            isCalculateButtonEnabled = false;
            return false;
        }

        isCalculateButtonEnabled = isInputAvailable();
        if (isCalculateButtonEnabled) {
            status = Status.READY;
        } else {
            status = Status.WAITING;
        }

        return isCalculateButtonEnabled;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    private String calculateLogMessage() {
        String message =
                LogMessages.CALCULATE_WAS_PRESSED + "Arguments"
                        + ": a = " + a
                        + "; b = " + b
                        + "; n = " + n + "."
                        + " Метод: " + method.toString() + ". "
                        + " Функция: " + function.toString() + ".";

        return message;
    }

    public void calculate() {
        logger.log(calculateLogMessage());

        if (!parseInput()) {
            return;
        }

        double a = Double.parseDouble(this.a);
        double b = Double.parseDouble(this.b);
        int n = Integer.parseInt(this.n);

        IntegrationMethods integration = new IntegrationMethods(n, a, b);

        final int const4 = 4;
        final int const10 = 10;
        final int const100 = 100;

        String f;
        double exactSolution;
        switch (function) {
            case function1:
                f = "1";
                exactSolution = Math.PI / const4;
                break;
            case function2:
                f = "2";
                exactSolution = Math.PI / const4 + Math.sin(const10) / const10;
                break;
            case function3:
                f = "3";
                exactSolution = Math.PI / const4 + Math.sin(const100) / const100;
                break;
            default:
                throw new IllegalArgumentException(
                        "Такая функция недоступна "
                                + "для вычисления!");
        }

        double res;
        switch (method) {
            case Rectangle:
                res = integration.rectangle(f);
                break;
            case Trapezoid:
                res = integration.trapezoid(f);
                break;
            case Simpson:
                res = integration.simpson(f);
                break;
            default:
                throw new IllegalArgumentException(
                        "Доступны только методы:"
                                + " прямоугольников,"
                                + " трапеций и Симпсона!");
        }

        result = Double.toString(res);
        error = Double.toString(Math.abs(res - exactSolution));
        status = ViewModel.Status.SUCCESS;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(final Method method) {
        if (this.method != method) {
            logger.log(LogMessages.METHOD_WAS_CHANGED + method.toString());
            this.method = method;
        }
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(final Function function) {
        if (this.function != function) {
            logger.log(LogMessages.FUNCTION_WAS_CHANGED + function.toString());
            this.function = function;
        }
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public String getA() {
        return a;
    }

    public void setA(final String a) {
        if (a.equals(this.a)) {
            return;
        }

        this.a = a;
        isInputChanged = true;
    }

    public String getB() {
        return b;
    }

    public void setB(final String b) {
        if (b.equals(this.b)) {
            return;
        }

        this.b = b;
        isInputChanged = true;
    }

    public String getN() {
        return n;
    }

    public void setN(final String n) {
        if (n.equals(this.n)) {
            return;
        }

        this.n = n;
        isInputChanged = true;
    }

    public String getStatus() {
        return status;
    }

    public enum Method {
        Rectangle("Прямоугольников"),
        Trapezoid("Трапеций"),
        Simpson("Симпсона");

        private final String name;

        Method(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public enum Function {
        function1("1/(1 + x^2)"),
        function2("1/(1 + x^2) + cos(10x)"),
        function3("1/(1 + x^2) + cos(100x)");

        private final String name;

        Function(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public final class Status {
        public static final String WAITING = "Ожидается ввод данных.";
        public static final String READY = "Нажмите 'Вычислить'";
        public static final String BAD_FORMAT = "Неверный формат данных.";
        public static final String SUCCESS = "Успешно вычислено.";

        private Status() { }
    }

    public final class LogMessages {
        public static final String CALCULATE_WAS_PRESSED =
                "Произведено вычисление.";
        public static final String METHOD_WAS_CHANGED =
                "Метод изменён на метод";
        public static final String FUNCTION_WAS_CHANGED =
                "Функция изменена на ";
        public static final String EDITING_FINISHED =
                "Входящие параметры изменены.";

        private LogMessages() { }
    }

    public boolean isEmpty(final String s) {
        return s.length() == 0;
    }
}
