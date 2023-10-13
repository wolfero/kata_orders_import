package es.publicotech.services;

import es.publicotech.models.API.ApiLinks;
import es.publicotech.models.API.ApiOrders;
import es.publicotech.models.Order;
import es.publicotech.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void shouldImportOrdersFromApiIntoDB() throws IOException, SQLException {
        String url = "http://api.example.com/orders";
        ApiOrders apiOrders = mock(ApiOrders.class);
        ApiLinks apiLinks = mock(ApiLinks.class);
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setUuid(UUID.randomUUID());
        order.setId("1");
        order.setRegion("Region");
        order.setCountry("Country");
        order.setItemType("ItemType");
        order.setSalesChannel("Channel");
        order.setPriority("High");
        order.setDate("7/28/2012");
        order.setShipDate("7/28/2012");
        order.setUnitsSold(10L);
        order.setUnitPrice(10.0);
        order.setUnitCost(5.0);
        order.setTotalRevenue(100.0);
        order.setTotalCost(50.0);
        order.setTotalProfit(50.);
        order.setSelf("link");
        orders.add(order);
        OrderService orderServiceSpy = spy(orderService);

        doReturn(apiOrders).when(orderServiceSpy).getApiOrders(anyString());
        doReturn(orders).when(apiOrders).getOrders();
        doReturn(apiLinks).when(apiOrders).getLinks();
        doReturn(url).when(apiLinks).getNext();

        orderServiceSpy.importOrdersFromApiIntoDB(url);

        orders.add(order);
        orders.add(order);
        orders.add(order);
        verify(orderRepository).saveOrders(orders);
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
