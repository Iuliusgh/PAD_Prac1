package es.ucm.fdi.calculator;

import static org.junit.Assert.*;

import org.junit.Test;

import es.ucm.fdi.calculator.Suma;

public class CalculatorUnitTest {
    @Test
    public void suma(){
        double suma = Suma.add(1,2);
        assertEquals(3,suma,1);
    }

    @Test
    public void suma1(){
        double suma = Suma.add(-5,9);
        assertEquals(4,suma,1);
    }

    @Test
    public void suma2(){
        double suma = Suma.add(-6,11);
        assertEquals(5,suma,1);
    }

    @Test
    public void suma3(){
        double suma = Suma.add(-1,2);
        assertEquals(1,suma,1);
    }
}
