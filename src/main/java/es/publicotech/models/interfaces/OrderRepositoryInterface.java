package es.publicotech.models.interfaces;

import es.publicotech.models.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepositoryInterface {
    void saveOrders(List<Order> orders) throws SQLException, IOException;

    List<Order> loadAllOrders() throws SQLException, IOException;
}
