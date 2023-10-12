package es.publicotech.repository;

import es.publicotech.database.DataBaseConnector;
import es.publicotech.models.Links;
import es.publicotech.models.Order;
import es.publicotech.models.interfaces.OrderRepositoryInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class OrderRepository implements OrderRepositoryInterface {
    private final DataBaseConnector dbConnector;

    @Override
    public void saveOrders(List<Order> orders) throws SQLException, IOException {
        String insertOrderSQL = "INSERT INTO ordersschema.orders (" +
                "uuid, id, region, country, item_type, sales_channel, " +
                "priority, date, ship_date, units_sold, unit_price, " +
                "unit_cost, total_revenue, total_cost, total_profit) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (uuid) DO UPDATE SET " +
                "id = excluded.id, region = excluded.region, country = excluded.country, " +
                "item_type = excluded.item_type, sales_channel = excluded.sales_channel, " +
                "priority = excluded.priority, date = excluded.date, ship_date = excluded.ship_date, " +
                "units_sold = excluded.units_sold, unit_price = excluded.unit_price, " +
                "unit_cost = excluded.unit_cost, total_revenue = excluded.total_revenue, " +
                "total_cost = excluded.total_cost, total_profit = excluded.total_profit;";

        String linksSql = "INSERT INTO ordersschema.links (order_uuid, self) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (order_uuid) DO UPDATE SET " +
                "self = excluded.self;";
        try (
                Connection connection = dbConnector.connectToDB();
                PreparedStatement ordersPreparedStatement = connection.prepareStatement(insertOrderSQL);
                PreparedStatement linksPreparedStatement = connection.prepareStatement(linksSql)
        ) {
            for (Order order : orders) {
                insertOrderValues(ordersPreparedStatement, order);
                ordersPreparedStatement.addBatch();
                insertLinkValues(linksPreparedStatement, order);
                linksPreparedStatement.addBatch();
            }
            ordersPreparedStatement.executeBatch();
            linksPreparedStatement.executeBatch();
        }
    }

    private void insertOrderValues(PreparedStatement preparedStatement, Order order) throws SQLException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");
        preparedStatement.setObject(1, order.getUuid());
        preparedStatement.setString(2, order.getId());
        preparedStatement.setString(3, order.getRegion());
        preparedStatement.setString(4, order.getCountry());
        preparedStatement.setString(5, order.getItemType());
        preparedStatement.setString(6, order.getSalesChannel());
        preparedStatement.setString(7, order.getPriority());
        preparedStatement.setDate(8, Date.valueOf(LocalDate.parse(order.getDate(), format)));
        preparedStatement.setDate(9, Date.valueOf(LocalDate.parse(order.getShipDate(), format)));
        preparedStatement.setLong(10, order.getUnitsSold());
        preparedStatement.setDouble(11, order.getUnitPrice());
        preparedStatement.setDouble(12, order.getUnitCost());
        preparedStatement.setDouble(13, order.getTotalRevenue());
        preparedStatement.setDouble(14, order.getTotalCost());
        preparedStatement.setDouble(15, order.getTotalProfit());
    }

    private void insertLinkValues(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setObject(1, order.getUuid());
        preparedStatement.setString(2, order.getLinks().getSelf());
    }

    @Override
    public List<Order> loadAllOrders() throws SQLException, IOException {
        List<Order> orders;
        try (
                Connection connection = dbConnector.connectToDB();
                PreparedStatement ordersStatement = connection.prepareStatement("SELECT * FROM ordersschema.orders");
                ResultSet ordersResultSet = ordersStatement.executeQuery();
                PreparedStatement linksStatement = connection.prepareStatement("SELECT * FROM ordersschema.links");
                ResultSet linksResultSet = linksStatement.executeQuery()
        ) {
            orders = loadOrdersValues(ordersResultSet);
            orders = loadLinksValues(linksResultSet, orders);
        }
        return orders;
    }

    private List<Order> loadOrdersValues(ResultSet resultSet) throws SQLException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setUuid((UUID) resultSet.getObject("uuid"));
            order.setId(resultSet.getString("id"));
            order.setRegion(resultSet.getString("region"));
            order.setCountry(resultSet.getString("country"));
            order.setItemType(resultSet.getString("item_type"));
            order.setSalesChannel(resultSet.getString("sales_channel"));
            order.setPriority(resultSet.getString("priority"));
            order.setDate(resultSet.getDate("date").toLocalDate().format(format));
            order.setShipDate(resultSet.getDate("ship_date").toLocalDate().format(format));
            order.setUnitsSold(resultSet.getLong("units_sold"));
            order.setUnitPrice(resultSet.getDouble("unit_price"));
            order.setUnitCost(resultSet.getDouble("unit_cost"));
            order.setTotalRevenue(resultSet.getDouble("total_revenue"));
            order.setTotalCost(resultSet.getDouble("total_cost"));
            order.setTotalProfit(resultSet.getDouble("total_profit"));
            orders.add(order);
        }
        return orders;
    }


    private List<Order> loadLinksValues(ResultSet resultSet, List<Order> orders) throws SQLException {
        List<Order> newOrders = new ArrayList<>();
        while (resultSet.next()) {
            newOrders = orders.stream().peek(order -> {
                try {
                    if (order.getUuid() == resultSet.getObject("order_uuid")) {
                        Links links = new Links(resultSet.getString("self"));
                        order.setLinks(links);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        }
        return newOrders;
    }
}
