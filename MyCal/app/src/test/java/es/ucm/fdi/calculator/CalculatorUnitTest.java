package es.ucm.fdi.calculator;

import org.junit.Test;
import static org.junit.Assert.*;
import es.ucm.fdi.calculator.Calculator;

public class CalculatorUnitTest {
    @Test
    public void addTwoDecimalNumbers(){
        assertEquals(3.1, Calculator.add(1.1,2.0), 0);

    }

    @Test
    public void addTwoNumbers(){
        assertEquals(3, Calculator.add(1,2), 0);
    }

    @Test
    public void addTwoNumbers1(){
        assertEquals(3, Calculator.add(1,2), 0);
    }
}
