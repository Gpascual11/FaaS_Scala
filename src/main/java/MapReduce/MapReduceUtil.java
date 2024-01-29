package MapReduce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
