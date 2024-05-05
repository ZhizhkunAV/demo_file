package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Работа с Zip-архивов")
public class ParsingFilesInZipTest {

    private final ClassLoader cl = ParsingFilesInZipTest.class.getClassLoader();
    private final ZipFile file;

    {
        try {
            file = new ZipFile(new File("src/test/resources/Archive.zip"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Проверка наличия архива")
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
    @DisplayName("В архиве Zip есть файл .xlsx")
    void xlsFileTest() throws Exception {

        ZipFile zipfile = new ZipFile("src/test/resources/Archive.zip");
        ZipEntry entry = zipfile.getEntry("xlsxFile.xlsx");
        InputStream inputStream = zipfile.getInputStream(entry);
        try {

            XLS xls = new XLS(inputStream);
            String actualValue = xls.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();
            assertEquals("may", actualValue);
        } finally {
            inputStream.close();
        }
    }

    @DisplayName("В архиве Zip есть файл .xlsx-2")
    @Test
    void zipXlsxTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("Archive.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("xlsxFile.xlsx")) {
                    try (InputStream is = file.getInputStream(entry)) {
                        XLS xls = new XLS(zis);
                        String actualValue = xls.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue();
                        Assertions.assertEquals("may", actualValue);
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("В архиве Zip есть файл .csv")
    void csvFileTest() throws Exception {

        ZipFile zipfile = new ZipFile(new File("src/test/resources/Archive.zip"));
        ZipEntry entry = zipfile.getEntry("CSV.csv");
        InputStream inputStream = zipfile.getInputStream(entry);
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> csvData = csvReader.readAll();
            Assertions.assertArrayEquals(new String[]{"acb;f;af;e3"}, csvData.get(2));
        } finally {
            inputStream.close();
        }
    }
    @Test
    @DisplayName("В архиве Zip есть файл .csv-2")
    void zipCsvTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("Archive.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("CSV.csv")) {
                    try (InputStream is = file.getInputStream(entry)) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(is));
                        List<String[]> csvData = csvReader.readAll();
                        Assertions.assertArrayEquals(new String[]{"acb;f;af;e3"}, csvData.get(2));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("В архиве Zip есть файл .pdf")
    void parsingPdfFileTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("Archive.zip")
        )) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("pdf.pdf")) {
                    try (InputStream is = file.getInputStream(entry)) {
                        PDF pdf = new PDF(is);
                        Assertions.assertEquals("Kseniya Sp", pdf.author);
                    }
                }
            }
        }
    }
}

