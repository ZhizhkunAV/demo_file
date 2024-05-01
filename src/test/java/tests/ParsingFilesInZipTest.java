package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@DisplayName("Работа с Zip-архивов")
public class ParsingFilesInZipTest {
    private ClassLoader cl = ParsingFilesInZipTest.class.getClassLoader();
    public static final Gson gson = new Gson();

    @DisplayName("Распарсиваем Zip архив и считываем названия файлов")
    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("Archive.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
        }
    }

    @Test
    @DisplayName("Архив имеет содержимое")
    void zipFileIsNotEmpty() throws Exception {
        try (InputStream inputStream = cl.getResourceAsStream("Archive.zip")) {
            assert inputStream != null;
        }
    }

    @Test
    @DisplayName("В архиве Zip есть файл .xlsx")
    void xlsFileTest() throws Exception {
        try (InputStream resource = cl.getResourceAsStream("Archive.zip")) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains("xlsxFile.xlsx")) {
                        XLS content = new XLS(zis);
                        String actualValue = content.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();
                        Assertions.assertEquals("may", actualValue);
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("В архиве Zip есть файл .csv")
    void csvFileTest() throws Exception {
        try (InputStream resource = cl.getResourceAsStream("Archive.zip")) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains("CSV.xlsx")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> csvData = csvReader.readAll();
                        Assertions.assertArrayEquals(new String[]{"acb;f;af;e3"}, csvData.get(2));
                    }

                }
            }
        }
    }

    @Test
    @DisplayName("В архиве Zip есть файл .pdf")
    void pdfFileTest() throws Exception {
        try (InputStream resource = cl.getResourceAsStream("Archive.zip")) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains("TextTextFileWord.pdf")) {
                        PDF pdf = new PDF(zis);
                        Assertions.assertEquals("Kseniya Sp", pdf.author);

                    }
                }
            }
        }
    }
}

