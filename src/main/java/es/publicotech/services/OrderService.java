package es.publicotech.services;

import es.publicotech.Main;
import es.publicotech.models.Order;
import es.publicotech.models.interfaces.OrderServiceInterface;
import es.publicotech.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import static es.publicotech.utils.CSVUtils.readOrdersFromCSV;
import static es.publicotech.utils.CSVUtils.writeOrdersToCSV;
import static es.publicotech.utils.ConsoleUtils.resume;

@Slf4j
@AllArgsConstructor
public class OrderService implements OrderServiceInterface {
    private OrderRepository orderRepository;

    @Override
    public void importOrdersFromCSVIntoDB(String importCsvPath) throws IOException, SQLException {
        try (InputStream inputStream = Main.class.getResourceAsStream(importCsvPath)) {
            List<Order> orders = readOrdersFromCSV(inputStream);
            orderRepository.saveOrders(orders);
            resume(orders);
            log.info("Orders were successfully imported!");
        }
    }

    @Override
    public void exportOrdersIntoCSV(String exportCsvPath) throws SQLException, IOException {
        List<Order> orders = orderRepository.loadAllOrders();
        if (orders != null) {
            List<Order> sortedOrders = getOrdersSortedById(orders);
            writeOrdersToCSV(sortedOrders, exportCsvPath);
            log.info("Orders were successfully exported into " + exportCsvPath + " file!");
        }
    }

    private List<Order> getOrdersSortedById(List<Order> orders) {
        orders.sort(Comparator.comparing(Order::getOrderId));
        return orders;
    }
}
