package MapReduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for MapReduce.
 */
public class MapReduceUtil {

    /**
     * Reads text from a file and returns it as a string.
     *
     * @param filePath The path to the file.
     * @return The text from the file.
     */
    public static String readTextFromFile(String filePath) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
