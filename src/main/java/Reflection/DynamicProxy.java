package Reflection;

import Components.Controller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Created by milax on 20/10/15.
 */
public class DynamicProxy implements InvocationHandler {
    private final Object target;
    private final Controller controller;

    /**
     * Constructor
     * @param target Object
     * @param controller Controller
     */
    public DynamicProxy(Object target, Controller controller) {
        this.controller = controller;
        this.target = target;
    }

    /**
     * Invoke method
     * @param proxy Object
     * @param method Method
     * @param args Object[]
     * @return Object
     * @throws Throwable Exception
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        String methodName = method.getName();

        Function<Object[], Object> function = (arg) -> {
            try {
                return method.invoke(target, arg);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        };

        controller.registerAction(methodName, function);
        return controller.invoke(methodName, args);
    }

}