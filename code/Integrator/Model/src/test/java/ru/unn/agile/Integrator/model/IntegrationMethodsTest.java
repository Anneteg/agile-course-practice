package ru.unn.agile.Integrator.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntegrationMethodsTest {

    @Test(expected = IllegalArgumentException.class)
    public void isIncorrectFunctionNumber() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        integration.rectangle("0");
    }

    @Test
    public void isSolutionsEqualMethodsFunctionsEqual() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);

        double solve1 = integration.rectangle("1");
        double solve2 = integration.rectangle("1");
        double delta = 0.00001;

        assertEquals(solve1, solve2, delta);
    }

    @Test
    public void isSolutionsDifferentFunctionsNotEqual() {
        IntegrationMethods integration1 = new IntegrationMethods(100, 0, 1);
        IntegrationMethods integration2 = new IntegrationMethods(100, 0, 1);

        double solve1 = integration1.rectangle("1");
        double solve2 = integration2.rectangle("2");

        assertNotEquals(solve1, solve2);
    }

    @Test
    public void isSolutionsDifferentMethodsEqual() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);

        double solve1 = integration.rectangle("2");
        double solve2 = integration.trapezoid("2");
        double delta = 0.0001;

        assertEquals(solve1, solve2, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenRectangleMethodFunction1() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4;
        double delta = 0.00001;
        assertEquals(integration.rectangle("1"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenRectangleMethodFunction2() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4 + Math.sin(10) / 10;
        double delta = 0.0001;
        assertEquals(integration.rectangle("2"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenRectangleMethodFunction3() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4 + Math.sin(100) / 100;
        double delta = 0.001;
        assertEquals(integration.rectangle("3"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenTrapezoidMethodFunction1() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4;
        double delta = 0.00001;
        assertEquals(integration.trapezoid("1"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenTrapezoidMethodFunction2() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4 + Math.sin(10) / 10;
        double delta = 0.0001;
        assertEquals(integration.trapezoid("2"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenTrapezoidMethodFunction3() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4 + Math.sin(100) / 100;
        double delta = 0.001;
        assertEquals(integration.trapezoid("3"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenSimpsonMethodFunction1() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4;
        double delta = 0.000001;
        assertEquals(integration.simpson("1"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenSimpsonMethodFunction2() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4 + Math.sin(10) / 10;
        double delta = 0.00001;
        assertEquals(integration.simpson("2"), exactSolution, delta);
    }

    @Test
    public void exactAndNumericalAreEqualWhenSimpsonMethodFunction3() {
        IntegrationMethods integration = new IntegrationMethods(100, 0, 1);
        double exactSolution = Math.PI / 4 + Math.sin(100) / 100;
        double delta = 0.0001;
        assertEquals(integration.simpson("3"), exactSolution, delta);
    }
}
