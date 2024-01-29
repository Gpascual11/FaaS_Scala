package TestJUnit;
import Components.Controller;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import java.util.function.Function;

public class TestMain {

    @Test
    public void testAddAction() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>();

        // Define input values and expected results
        Map<String, Integer> input1 = Map.of("x", 10, "y", 5);
        Map<String, Integer> input2 = Map.of("x", 2, "y", 3);
        Map<String, Integer> input3 = Map.of("x", 9, "y", 1);
        Map<String, Integer> input4 = Map.of("x", 8, "y", 8);

        int expectedRes1 = 5;  // Result for input1
        int expectedRes2 = -1; // Result for input2
        int expectedRes3 = 8;  // Result for input3
        int expectedRes4 = 0;  // Result for input4

        // Register action
        Function<Map<String, Integer>, Integer> f = x -> x.get("x") - x.get("y");
        controller.registerAction("addAction", f);

        // Test the invocation and assert the results
        assertEquals(expectedRes1, (int) controller.invoke("addAction", input1));
        assertEquals(expectedRes2, (int) controller.invoke("addAction", input2));
        assertEquals(expectedRes3, (int) controller.invoke("addAction", input3));
        assertEquals(expectedRes4, (int) controller.invoke("addAction", input4));
    }
}
