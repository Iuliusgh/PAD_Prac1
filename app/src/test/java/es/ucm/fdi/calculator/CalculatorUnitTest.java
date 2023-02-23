package es.ucm.fdi.calculator;

import org.junit.Test;
import static org.junit.Assert.*;
import es.ucm.fdi.calculator.Calculator;

public class CalculatorUnitTest {
    @Test
    public void addTwoNumbers(){
        double res = Calculator.add(1.1,2.0);
        assertEquals(3.1, res, 1);
    }
}
