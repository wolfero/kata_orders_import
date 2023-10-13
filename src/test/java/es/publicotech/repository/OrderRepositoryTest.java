package es.publicotech.repository;

import es.publicotech.database.DataBaseConnector;
import es.publicotech.models.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderRepositoryTest {
    @Test
    void shouldSaveOrders() throws SQLException, IOException {
        DataBaseConnector dbConnectorMock = mock(DataBaseConnector.class);
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
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
        when(dbConnectorMock.connectToDB()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        OrderRepository orderRepository = new OrderRepository(dbConnectorMock);

        orderRepository.saveOrders(Collections.singletonList(order));

        verify(preparedStatementMock, times(1)).addBatch();
        verify(preparedStatementMock, times(1)).executeBatch();
    }

    @Test
    void shouldLoadOrders() throws SQLException, IOException {
        DataBaseConnector dbConnectorMock = mock(DataBaseConnector.class);
        Connection connectionMock = mock(Connection.class);
        PreparedStatement preparedStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        when(dbConnectorMock.connectToDB()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getObject("uuid")).thenReturn(UUID.randomUUID());
        when(resultSetMock.getString("id")).thenReturn("1");
        when(resultSetMock.getString("region")).thenReturn("Region");
        when(resultSetMock.getString("country")).thenReturn("country");
        when(resultSetMock.getString("item_type")).thenReturn("item_type");
        when(resultSetMock.getString("sales_channel")).thenReturn("sales_channel");
        when(resultSetMock.getString("priority")).thenReturn("priority");
        when(resultSetMock.getDate("date")).thenReturn(Date.valueOf("2012-07-12"));
        when(resultSetMock.getDate("ship_date")).thenReturn(Date.valueOf("2012-07-12"));
        when(resultSetMock.getLong("units_sold")).thenReturn(10L);
        when(resultSetMock.getDouble("unit_price")).thenReturn(10.0);
        when(resultSetMock.getDouble("unit_cost")).thenReturn(10.0);
        when(resultSetMock.getDouble("total_revenue")).thenReturn(10.0);
        when(resultSetMock.getDouble("total_cost")).thenReturn(10.0);
        when(resultSetMock.getDouble("total_profit")).thenReturn(10.0);
        when(resultSetMock.getString("self")).thenReturn("link");

        OrderRepository orderRepository = new OrderRepository(dbConnectorMock);

        List<Order> orders = orderRepository.loadAllOrders();

        assertEquals(1, orders.size());
    }

}
