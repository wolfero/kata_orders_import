package es.publicotech.utils;

import es.publicotech.models.Order;
import es.publicotech.models.enums.ExportOrderHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class CSVUtils {
    public static void writeOrdersToCSV(List<Order> orders, String outputPath) throws IOException {
        createExportFileIfNotExist(outputPath);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath));
             CSVPrinter csvPrinter = initializeCSVPrinter(writer)) {
            for (Order order : orders) {
                mapOrderIntoCSVPrinter(csvPrinter, order);
            }
            csvPrinter.flush();
        }
    }

    private static void createExportFileIfNotExist(String outputPath) throws IOException {
        Path outputFilePath = Paths.get(outputPath);
        Files.createDirectories(outputFilePath.getParent());
    }

    private static CSVPrinter initializeCSVPrinter(BufferedWriter write) throws IOException {
        return new CSVPrinter(write, CSVFormat.DEFAULT.builder()
                .setHeader(ExportOrderHeaders.class)
                .build()
        );
    }

    private static void mapOrderIntoCSVPrinter(CSVPrinter csvPrinter, Order order) throws IOException {
        csvPrinter.printRecord(
                order.getUuid(),
                order.getId(),
                order.getRegion(),
                order.getCountry(),
                order.getItemType(),
                order.getSalesChannel(),
                order.getPriority(),
                order.getDate(),
                order.getShipDate(),
                order.getUnitsSold(),
                order.getUnitPrice(),
                order.getUnitCost(),
                order.getTotalRevenue(),
                order.getTotalCost(),
                order.getTotalProfit()
        );
    }
}
