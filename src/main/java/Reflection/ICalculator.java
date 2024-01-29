package Reflection;

/**
 * Created by milax on 20/10/15.
 */
public interface ICalculator {

    /**
     * @param num1 number 1
     * @param num2 number 2
     * @return num1 + num2
     */
    double add (double num1, double num2);

    /**
     * @param num1 number 1
     * @param num2 number 2
     * @return num1 - num2
     */
    double subtract (double num1, double num2);

    /**
     * @param num1 number 1
     * @param num2 number 2
     * @return num1 * num2
     */
    double multiply (double num1, double num2);

    /**
     * @param num1 number 1
     * @param num2 number 2
     * @return num1 / num2
     */
    double divide (double num1, double num2);
}
