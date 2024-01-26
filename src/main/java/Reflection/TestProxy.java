package Reflection;

public class TestProxy {
    public static void main(String[] args) {
        // Create a Controller
        /*IControllerProxy proxy = (IControllerProxy) DynamicProxy.newInstance(new Controller<>(5));

        // Register a sleep function
        Function<Integer, String> printAnything = print -> {
            for (int i = 0; i < print; i++) {
                System.out.println("Hello, World!");
                try {
                    Thread.sleep( 1000L); // Convert seconds to milliseconds
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return print + " Done!";
        };

        // Register the sleep function with a specific amount of RAM
        proxy.registerAction("printAny", printAnything, 150);

        // Invoke the proxy
        proxy.invoke("printAny", 5);*/
    }
}