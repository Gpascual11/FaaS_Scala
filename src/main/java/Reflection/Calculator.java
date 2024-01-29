package Reflection;

/**
 * Created by milax on 20/10/15.
 */
public class Calculator implements ICalculator {

    /**
     * Function to add two numbers
     * @param num1 numbers to add
     * @param num2 numbers to add
     * @return num1 + num2
     */
    public double add (double num1, double num2){
        return num1 + num2;
    }

    /**
     * Function to subtract two numbers
     * @param num1 numbers to subtract
     * @param num2 numbers to subtract
     * @return num1 - num2
     */
    public double subtract (double num1, double num2){
        return num1 - num2;
    }

    /**
     * Function to multiply two numbers
     * @param num1 numbers to multiply
     * @param num2 numbers to multiply
     * @return num1 * num2
     */
    public double multiply (double num1, double num2){
        return num1 * num2;
    }

    /**
     * Function to divide two numbers
     * @param num1 numbers to divide
     * @param num2 numbers to divide
     * @return num1 / num2
     */
    public double divide (double num1, double num2){
        return num1/num2;
    }
}
