package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    DigitCountServer digitCountServer;
    @BeforeEach
    public void initEach(){ digitCountServer = new DigitCountServer();
    }

    @org.junit.jupiter.api.Test
    void solverFalse() {
        int expected = 3;
        int actual = digitCountServer.solver(1, 30,5, false);
        assertEquals(expected, actual, "All digits false");
    }
    @Test
    void solverTrue(){
        int expected = 13;
        int actual = digitCountServer.solver(1,22,1,true);
        assertEquals(expected, actual, "All digits true");
    }
    @Test
    void solverOneFalse(){
        int expected = 12;
        int actual = digitCountServer.solver(1,22,1,false);
        assertEquals(expected, actual, "All digits false");
    }
}