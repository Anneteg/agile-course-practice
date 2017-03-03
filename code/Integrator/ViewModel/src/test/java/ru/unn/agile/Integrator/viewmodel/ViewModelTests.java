package ru.unn.agile.Integrator.viewmodel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getA());
        assertEquals("", viewModel.getB());
        assertEquals("", viewModel.getN());
        Assert.assertEquals(ViewModel.Method.Rectangle, viewModel.getMethod());
        Assert.assertEquals(ViewModel.Function.function1, viewModel.getFunction());
        assertEquals("", viewModel.getResult());
        assertEquals("", viewModel.getError());
        Assert.assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        Assert.assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();

        Assert.assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFill() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        Assert.assertEquals(ViewModel.Status.READY, viewModel.getStatus());
    }

    private void fillInputFields() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
    }

    @Test
    public void canReportBadFormat() {
        viewModel.setA("a");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        Assert.assertEquals(ViewModel.Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseIsOK() {
        viewModel.setA("a");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);
        viewModel.setA("1.0");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        Assert.assertEquals(ViewModel.Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isCalculateButtonDisabledInitially() {
        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenFormatIsBad() {
        fillInputFields();
        viewModel.processKeyInTextField(KeyboardKeys.ANY);
        assertEquals(true, viewModel.isCalculateButtonEnabled());

        viewModel.setA("trash");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWithIncompleteInput() {
        viewModel.setA("0");
        viewModel.setB("1");

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void canGetOperationName() {
        String rectangleName = ViewModel.Method.Rectangle.toString();
        assertEquals("Прямоугольников", rectangleName);
    }

    @Test
    public void canGetFunctionName() {
        String firstFunctionName = ViewModel.Function.function1.toString();
        assertEquals("1/(1 + x^2)", firstFunctionName);
    }

    @Test
    public void canGetNumberOfOperations() {
        int nOperations = ViewModel.Method.values().length;
        assertEquals(3, nOperations);
    }

    @Test
    public void canGetNumberOfFunctions() {
        int nFunctions = ViewModel.Function.values().length;
        assertEquals(3, nFunctions);
    }

    @Test
    public void canGetListOfMethods() {
        ViewModel.Method[] methods = ViewModel.Method.values();
        ViewModel.Method[] currentMethods = new ViewModel.Method[]{
                ViewModel.Method.Rectangle,
                ViewModel.Method.Trapezoid,
                ViewModel.Method.Simpson};

        assertArrayEquals(currentMethods, methods);
    }

    @Test
    public void canGetListOfFunctions() {
        ViewModel.Function[] functions = ViewModel.Function.values();
        ViewModel.Function[] currentFunctions = new ViewModel.Function[]{
                ViewModel.Function.function1,
                ViewModel.Function.function2,
                ViewModel.Function.function3};

        assertArrayEquals(currentFunctions, functions);
    }

    @Test
    public void isCalculateButtonEnabledWithCorrectInput() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(true, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void canSetRectangleMethod() {
        viewModel.setMethod(ViewModel.Method.Rectangle);
        Assert.assertEquals(ViewModel.Method.Rectangle, viewModel.getMethod());
    }

    @Test
    public void canSetTrapezoidMethod() {
        viewModel.setMethod(ViewModel.Method.Trapezoid);
        Assert.assertEquals(ViewModel.Method.Trapezoid, viewModel.getMethod());
    }
    @Test
    public void canSetSimpsonMethod() {
        viewModel.setMethod(ViewModel.Method.Simpson);
        Assert.assertEquals(ViewModel.Method.Simpson, viewModel.getMethod());
    }

    @Test
    public void canSetFunction1() {
        viewModel.setFunction(ViewModel.Function.function1);
        Assert.assertEquals(ViewModel.Function.function1, viewModel.getFunction());
    }

    @Test
    public void canSetFunction2() {
        viewModel.setFunction(ViewModel.Function.function2);
        Assert.assertEquals(ViewModel.Function.function2, viewModel.getFunction());
    }
    @Test
    public void canSetFunction3() {
        viewModel.setFunction(ViewModel.Function.function3);
        Assert.assertEquals(ViewModel.Function.function3, viewModel.getFunction());
    }

    @Test
    public void canSetSuccessMessage() {
        fillInputFields();

        viewModel.calculate();

        Assert.assertEquals(ViewModel.Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canRectangleCalculateFunction1() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Rectangle);
        viewModel.setFunction(ViewModel.Function.function1);

        viewModel.calculate();

        double exactSolution = Math.PI / 4;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canTrapezoidCalculateFunction1() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Trapezoid);
        viewModel.setFunction(ViewModel.Function.function1);

        viewModel.calculate();

        double exactSolution = Math.PI / 4;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canSimpsonCalculateFunction1() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Simpson);
        viewModel.setFunction(ViewModel.Function.function1);

        viewModel.calculate();

        double exactSolution = Math.PI / 4;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canRectangleCalculateFunction2() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Rectangle);
        viewModel.setFunction(ViewModel.Function.function2);

        viewModel.calculate();

        double exactSolution = Math.PI / 4 + Math.sin(10) / 10;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canTrapezoidCalculateFunction2() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Trapezoid);
        viewModel.setFunction(ViewModel.Function.function2);

        viewModel.calculate();

        double exactSolution = Math.PI / 4 + Math.sin(10) / 10;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canSimpsonCalculateFunction2() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Simpson);
        viewModel.setFunction(ViewModel.Function.function2);

        viewModel.calculate();

        double exactSolution = Math.PI / 4 + Math.sin(10) / 10;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canRectangleCalculateFunction3() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Rectangle);
        viewModel.setFunction(ViewModel.Function.function3);

        viewModel.calculate();

        double exactSolution = Math.PI / 4 + Math.sin(100) / 100;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canTrapezoidCalculateFunction3() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Trapezoid);
        viewModel.setFunction(ViewModel.Function.function3);

        viewModel.calculate();

        double exactSolution = Math.PI / 4 + Math.sin(100) / 100;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }

    @Test
    public void canSimpsonCalculateFunction3() {
        viewModel.setA("0");
        viewModel.setB("1");
        viewModel.setN("100");
        viewModel.setMethod(ViewModel.Method.Simpson);
        viewModel.setFunction(ViewModel.Function.function3);

        viewModel.calculate();

        double exactSolution = Math.PI / 4 + Math.sin(100) / 100;
        double error = Double.parseDouble(viewModel.getError());
        double result = Double.parseDouble(viewModel.getResult());

        assertEquals(result, exactSolution, error);
    }


}
