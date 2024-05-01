package tests.lesson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.lesson.model.Glossary;

import java.io.InputStreamReader;
import java.io.Reader;


public class FilesParsingTest {

    private ClassLoader cl = FilesParsingTest.class.getClassLoader();
    private static final Gson gson = new Gson();
    @Test
    void jsonFileParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("glossary.json")
        )) {
            JsonObject actual = gson.fromJson(reader, JsonObject.class);

            Assertions.assertEquals("example glossary", actual.get("title").getAsString());
            Assertions.assertEquals(234234, actual.get("ID").getAsInt());

            JsonObject inner = actual.get("glossary").getAsJsonObject();

            Assertions.assertEquals("SGML", inner.get("SortAs").getAsString());
            Assertions.assertEquals("Standard Generalized Markup Language", inner.get("GlossTerm").getAsString());
        }
    }

    @Test
    void jsonFileParsingImprovedTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("glossary.json")
        )) {
            Glossary actual = gson.fromJson(reader, Glossary.class);

            Assertions.assertEquals("example glossary", actual.getTitle());
            Assertions.assertEquals(234234, actual.getID());
            Assertions.assertEquals("SGML", actual.getGlossary().getSortAs());
            Assertions.assertEquals("Standard Generalized Markup Language", actual.getGlossary().getGlossTerm());
        }
    }
}
