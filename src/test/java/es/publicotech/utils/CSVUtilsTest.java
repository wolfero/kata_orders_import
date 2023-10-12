package es.publicotech.utils;

import es.publicotech.models.Order;
import es.publicotech.models.enums.ImportOrderHeaders;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVUtilsTest {

    @Test
    void shouldReadOrdersFromCSV() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/import/RegistroVentas1.csv");

        List<Order> orders = CSVUtils.readOrdersFromCSV(inputStream);

        int expectedSize = 1000;
        assertEquals(expectedSize, orders.size());
    }

    @Test
    void shouldWriteOrdersToCSV() throws IOException {
        Order order1 = new Order(1, "High", LocalDate.of(2021, 1, 1), "Region1", "Country1", "ItemType1", "Channel1", LocalDate.of(2021, 2, 1), 10, 100.0, 50.0, 1000.0, 500.0, 500.0);
        Order order2 = new Order(2, "Low", LocalDate.of(2021, 3, 1), "Region2", "Country2", "ItemType2", "Channel2", LocalDate.of(2021, 4, 1), 20, 200.0, 100.0, 4000.0, 2000.0, 2000.0);
        List<Order> orders = Arrays.asList(order1, order2);
        String outputPath = "src/test/resources/export/test_output.csv";
        CSVUtils.writeOrdersToCSV(orders, outputPath);

        try (FileReader reader = new FileReader(outputPath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                     .setHeader(ImportOrderHeaders.class)
                     .setSkipHeaderRecord(true)
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .build())
        ) {
            List<CSVRecord> records = csvParser.getRecords();

            assertEquals(orders.size(), records.size());
        }

        Files.deleteIfExists(Paths.get(outputPath));
    }
}


