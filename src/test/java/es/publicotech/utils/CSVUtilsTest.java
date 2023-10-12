package es.publicotech.utils;

import es.publicotech.models.Order;
import es.publicotech.models.enums.ImportOrderHeaders;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVUtilsTest {

    @Test
    void shouldWriteOrdersToCSV() throws IOException {
        Order order1 = new Order();
        order1.setUuid(UUID.randomUUID());
        Order order2 = new Order();
        order2.setUuid(UUID.randomUUID());
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


