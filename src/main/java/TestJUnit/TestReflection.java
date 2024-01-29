package TestJUnit;

import Components.Controller;
import Reflection.Calculator;
import Reflection.ICalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestReflection {
    ICalculator dp;
    @BeforeEach
    void setUp() {
        Controller controller = new Controller();
        ICalculator c = new Calculator();
        dp = (ICalculator)controller.createProxy(c);
    }

    @Test
    void testProxyInvocationAdd() {
        System.out.println("-> TESTING ADD...");
        assert(dp.add(3, 3) == 6.0);


    }

    @Test
    void testProxyInvocationSubtract() {
        System.out.println("-> TESTING SUBTRACT...");
        assert (dp.subtract(5, 10) == -5);
    }

    @Test
    void testProxyInvocationMultiply() {
        System.out.println("-> TESTING MULTIPLY...");
        assert (dp.multiply(3, 4) == 12);
    }

    @Test
    void testProxyInvocationDivide() {
        System.out.println("-> TESTING DIVIDE...");
        assert (dp.divide(9, 12) == 0.75);
    }
}
