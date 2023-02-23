package es.ucm.fdi.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorUnitTest {
    @Test
    public void addTwoDecimalNumbers(){
        assertEquals(3.1, Calculator.add(1.1,2.0), 0);

    }

    @Test
    public void addTwoDecimalNumbers1(){
        assertEquals(0, Calculator.add(-1.1,1.1), 0);

    }

    @Test
    public void addTwoDecimalNumbers2(){
        assertEquals(5, Calculator.add(2.5,2.5), 0);
    }

    @Test
    public void addTwoNumbers(){
        assertEquals(3, Calculator.add(1,2), 0);
    }

    @Test
    public void addTwoNumbers1(){
        assertEquals(5, Calculator.add(-3,8), 0);
    }

    @Test
    public void addTwoNumbers2(){
        assertEquals(0, Calculator.add(-1,1), 0);
    }

}
