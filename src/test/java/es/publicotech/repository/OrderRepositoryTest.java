package es.publicotech.repository;

import es.publicotech.database.DataBaseConnector;
import es.publicotech.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

public class OrderRepositoryTest {
    private DataBaseConnector dbConnector;
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        dbConnector = mock(DataBaseConnector.class);
        orderRepository = new OrderRepository(dbConnector);
    }

    @Test
    public void shouldSaveOrders() throws SQLException, IOException {
        Order order = new Order(1, "High", LocalDate.now(), "Region", "Country", "ItemType", "Channel", LocalDate.now(), 10, 10.0, 5.0, 100.0, 50.0, 50.0);
        List<Order> orders = List.of(order);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(dbConnector.connectToDB()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        orderRepository.saveOrders(orders);

        verify(dbConnector).connectToDB();
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement, times(orders.size())).addBatch();
        verify(mockPreparedStatement).executeBatch();
    }
}
