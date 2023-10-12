package es.publicotech.services;

import es.publicotech.models.Order;
import es.publicotech.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Order order1 = new Order();
        order1.setId("1");
        orders.add(order1);
        Order order2 = new Order();
        order2.setId("2");
        orders.add(order2);
        when(orderRepository.loadAllOrders()).thenReturn(orders);

        orderService.exportOrdersIntoCSV(exportCsvPath);

        verify(orderRepository).loadAllOrders();

        Files.deleteIfExists(Paths.get(exportCsvPath));
    }
}
