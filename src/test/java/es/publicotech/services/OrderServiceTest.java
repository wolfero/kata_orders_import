package es.publicotech.services;

import es.publicotech.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        // TODO
    }
}
