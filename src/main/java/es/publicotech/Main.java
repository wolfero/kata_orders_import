package es.publicotech;

import es.publicotech.database.DataBaseConnector;
import es.publicotech.repository.OrderRepository;
import es.publicotech.services.OrderService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final String importCsvPath = "/import/RegistroVentas1.csv"; //small
    //    private static final String importCsvPath = "/import/RegistroVentas2.csv"; //big
    private static final String exportCsvPath = "src/main/resource/export/ExportedOrders.csv";

    public static void main(String[] args) throws SQLException, IOException {
        DataBaseConnector dbConnector = new DataBaseConnector();
        OrderRepository orderRepository = new OrderRepository(dbConnector);
        OrderService orderService = new OrderService(orderRepository);
        orderService.importOrdersFromCSVIntoDB(importCsvPath);
        orderService.exportOrdersIntoCSV(exportCsvPath);
    }
}