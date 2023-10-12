package es.publicotech.repository;

import es.publicotech.database.DataBaseConnector;
import es.publicotech.models.Order;
import es.publicotech.models.interfaces.OrderRepositoryInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class OrderRepository implements OrderRepositoryInterface {
    private final DataBaseConnector dbConnector;

    @Override
    public void saveOrders(List<Order> orders) throws SQLException, IOException {
        String insertOrderSQL = "INSERT INTO ordersschema.orders (order_id, order_priority, order_date, region, country, item_type, sales_channel, ship_date, units_sold, unit_price, unit_cost, total_revenue, total_cost, total_profit)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "ON CONFLICT (order_id) " +
                "DO UPDATE SET order_priority = ?, order_date = ?, region = ?, country = ?, item_type = ?, sales_channel = ?, ship_date = ?, units_sold = ?, unit_price = ?, unit_cost = ?, total_revenue = ?, total_cost = ?, total_profit = ?";
        try (
                Connection connection = dbConnector.connectToDB();
                PreparedStatement preparedStatement = connection.prepareStatement(insertOrderSQL)
        ) {
            for (Order order : orders) {
                insertValues(preparedStatement, order);
                updateValues(preparedStatement, order);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertValues(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setInt(1, order.getOrderId());
        preparedStatement.setString(2, order.getOrderPriority());
        preparedStatement.setObject(3, order.getOrderDate());
        preparedStatement.setString(4, order.getRegion());
        preparedStatement.setString(5, order.getCountry());
        preparedStatement.setString(6, order.getItemType());
        preparedStatement.setString(7, order.getSalesChannel());
        preparedStatement.setObject(8, order.getShipDate());
        preparedStatement.setInt(9, order.getUnitsSold());
        preparedStatement.setDouble(10, order.getUnitPrice());
        preparedStatement.setDouble(11, order.getUnitCost());
        preparedStatement.setDouble(12, order.getTotalRevenue());
        preparedStatement.setDouble(13, order.getTotalCost());
        preparedStatement.setDouble(14, order.getTotalProfit());
    }

    private void updateValues(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setString(15, order.getOrderPriority());
        preparedStatement.setObject(16, order.getOrderDate());
        preparedStatement.setString(17, order.getRegion());
        preparedStatement.setString(18, order.getCountry());
        preparedStatement.setString(19, order.getItemType());
        preparedStatement.setString(20, order.getSalesChannel());
        preparedStatement.setObject(21, order.getShipDate());
        preparedStatement.setInt(22, order.getUnitsSold());
        preparedStatement.setDouble(23, order.getUnitPrice());
        preparedStatement.setDouble(24, order.getUnitCost());
        preparedStatement.setDouble(25, order.getTotalRevenue());
        preparedStatement.setDouble(26, order.getTotalCost());
        preparedStatement.setDouble(27, order.getTotalProfit());
    }

    @Override
    public List<Order> loadAllOrders() throws SQLException, IOException {
        return null;
    }
}
