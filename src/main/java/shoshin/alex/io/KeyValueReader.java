package shoshin.alex.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexander_Shoshin
 */
public class KeyValueReader {
    private static final String fieldSeparator = "\\s";
    
    public static Map<String, String> read(File file) throws FileNotFoundException, IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            return read(input);
        }
    }
    
    private static Map<String, String> read(BufferedReader input) throws IOException {
        Map<String, String> keyValue = new HashMap<String, String>();
        String record = "";
        while ((record = input.readLine()) != null) {
            String[] fields = record.split(fieldSeparator);
            if (fields.length != 2) {
                continue;
            }
            keyValue.put(fields[0], fields[1]);
        }
        return keyValue;
    }
}