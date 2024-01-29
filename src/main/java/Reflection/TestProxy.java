package Reflection;

import Components.Controller;

public class TestProxy {
    public static void main(String[] args) {
        Controller controller = new Controller();
        ICalculator c = new Calculator();
        ICalculator dp = (ICalculator)controller.createProxy(c);

        System.out.println("-> TESTING ADD...");
        System.out.println(dp.add(3, 3));
        System.out.println("-> TESTING SUBTRACT...");
        System.out.println(dp.subtract(5, 10));
        System.out.println("-> TESTING MULTIPLY...");
        System.out.println(dp.multiply(3, 4));
        System.out.println("-> TESTING DIVIDE...");
        System.out.println(dp.divide(9, 12));

    }
}