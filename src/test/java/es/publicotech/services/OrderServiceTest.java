package es.publicotech.services;

import es.publicotech.models.Order;
import es.publicotech.repository.OrderRepository;
import es.publicotech.utils.CSVUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void shouldImportOrdersFromCSVIntoDB() throws IOException, SQLException {
        String importCsvPath = "/import/RegistroVentas1.csv";

        orderService.importOrdersFromCSVIntoDB(importCsvPath);

        verify(orderRepository).saveOrders(anyList());
    }

    @Test
    public void shouldExportOrdersIntoCSV() throws SQLException, IOException {
        String exportCsvPath = "src/test/resources/export/ExportedOrders.csv";
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order(1, "High", LocalDate.now(), "Region", "Country", "ItemType", "Channel", LocalDate.now(), 10, 10.0, 5.0, 100.0, 50.0, 50.0);
        orders.add(order1);
        Order order2 = new Order(2, "High", LocalDate.now(), "Region", "Country", "ItemType", "Channel", LocalDate.now(), 10, 10.0, 5.0, 100.0, 50.0, 50.0);
        orders.add(order2);
        when(orderRepository.loadAllOrders()).thenReturn(orders);

        orderService.exportOrdersIntoCSV(exportCsvPath);

        verify(orderRepository).loadAllOrders();

        Files.deleteIfExists(Paths.get(exportCsvPath));
    }
}
