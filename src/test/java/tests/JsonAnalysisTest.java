package tests;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Парсинг Json")
public class JsonAnalysisTest {

    @Test
    @DisplayName("Распарсиваем Json файл используя библиотеку Jackson")
    void parseJsonTest() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/exam.json")));

        JsonFactory jFactory = new JsonFactory();
        JsonParser jParser = jFactory.createParser(json);

        String parsedTitle;
        List<String> names = new LinkedList<String>();

        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jParser.getCurrentName();

            if ("title".equals(fieldName)) {
                jParser.nextToken();
                parsedTitle = jParser.getText();
                assertEquals(parsedTitle, "Some people");
            }

            if ("names".equals(fieldName)) {
                jParser.nextToken();
                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    names.add(jParser.getText());
                }
                assertEquals(names, Arrays.asList("Kevin", "Olaf", "Eric"));
            }
        }
        jParser.close();
    }
}
