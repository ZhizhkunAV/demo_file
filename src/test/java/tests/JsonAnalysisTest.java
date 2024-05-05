package tests;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.model.TitleModel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Парсинг Json")
public class JsonAnalysisTest {

    private final ClassLoader cl = ParsingFilesInZipTest.class.getClassLoader();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @DisplayName("Парсинг Json с cl и геттерами-сеттерами")
    @Test
    void jsonFileParsTest() throws Exception {
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("exam.json"))) {
            TitleModel actual = objectMapper.readValue(reader, TitleModel.class);
            assertThat(actual.getNames()).isEqualTo("Kevin");
        }
    }



    @Test
    @DisplayName("Распарсиваем Json файл используя библиотеку Jackson")
    void parseJsonTest() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/example.json")));

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
