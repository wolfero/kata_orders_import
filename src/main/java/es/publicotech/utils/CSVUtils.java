package es.publicotech.utils;

import es.publicotech.models.Order;
import es.publicotech.models.enums.ImportOrderHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CSVUtils {
    public static List<Order> readOrdersFromCSV(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = initializeCSVParser(reader)) {
            List<Order> orders = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                Order order = mapCSVRecordIntoOrder(csvRecord);
                orders.add(order);
            }
            return orders;
        }
    }

    private static CSVParser initializeCSVParser(Reader reader) throws IOException {
        return new CSVParser(reader, CSVFormat.DEFAULT.builder()
                .setHeader(ImportOrderHeaders.class)
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build()
        );
    }

    private static Order mapCSVRecordIntoOrder(CSVRecord csvRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return new Order(
                Integer.parseInt(csvRecord.get(ImportOrderHeaders.ORDER_ID)),
                csvRecord.get(ImportOrderHeaders.ORDER_PRIORITY),
                LocalDate.parse(csvRecord.get(ImportOrderHeaders.ORDER_DATE), formatter),
                csvRecord.get(ImportOrderHeaders.REGION),
                csvRecord.get(ImportOrderHeaders.COUNTRY),
                csvRecord.get(ImportOrderHeaders.ITEM_TYPE),
                csvRecord.get(ImportOrderHeaders.SALES_CHANNEL),
                LocalDate.parse(csvRecord.get(ImportOrderHeaders.SHIP_DATE), formatter),
                Integer.parseInt(csvRecord.get(ImportOrderHeaders.UNITS_SOLD)),
                Double.parseDouble(csvRecord.get(ImportOrderHeaders.UNIT_PRICE)),
                Double.parseDouble(csvRecord.get(ImportOrderHeaders.UNIT_COST)),
                Double.parseDouble(csvRecord.get(ImportOrderHeaders.TOTAL_REVENUE)),
                Double.parseDouble(csvRecord.get(ImportOrderHeaders.TOTAL_COST)),
                Double.parseDouble(csvRecord.get(ImportOrderHeaders.TOTAL_PROFIT))
        );
    }

}
