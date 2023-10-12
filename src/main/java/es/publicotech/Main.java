package es.publicotech;

import es.publicotech.database.DataBaseConnector;
import es.publicotech.repository.OrderRepository;
import es.publicotech.services.OrderService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final String URL = "https://kata-espublicotech.g3stiona.com/v1/orders?page=1&max-per-page=1000";
    private static final String exportCsvPath = "src/main/resources/export/ExportedOrders.csv";

    public static void main(String[] args) throws SQLException, IOException {
        DataBaseConnector dbConnector = new DataBaseConnector();
        OrderRepository orderRepository = new OrderRepository(dbConnector);
        OrderService orderService = new OrderService(orderRepository);
        orderService.importOrdersFromApiIntoDB(URL);
        orderService.exportOrdersIntoCSV(exportCsvPath);
    }
}