package es.ucm.fdi.calculator;

import org.junit.Test;
import static org.junit.Assert.*;
import es.ucm.fdi.calculator.Calculator;

public class CalculatorUnitTest {
    @Test
    public void addTwoNumbers(){
        double res = Calculator.add(1,2);
        assertEquals(3, res, 1);
    }
}
