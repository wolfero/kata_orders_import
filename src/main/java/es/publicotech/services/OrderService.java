package es.publicotech.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.publicotech.models.API.ApiOrders;
import es.publicotech.models.Order;
import es.publicotech.models.interfaces.OrderServiceInterface;
import es.publicotech.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static es.publicotech.utils.CSVUtils.writeOrdersToCSV;
import static es.publicotech.utils.ConsoleUtils.resume;

@Slf4j
@AllArgsConstructor
public class OrderService implements OrderServiceInterface {
    private OrderRepository orderRepository;

    @Override
    public void importOrdersFromApiIntoDB(String url) throws IOException, SQLException {
        ApiOrders apiOrders = getApiOrders(url);
        List<Order> ordersToSave = new ArrayList<>();
        long orderCount = 0;
//--------------------------------------------------------------------------------------------------------------------------------------------//
//----------WARNING, THE API HAS MORE THAN HALF A MILLION RECORDS THAT WILL BE LOADED INTO THE DATABASE AND THEN INTO THE DUMP FILE---------//
//----------------------------------------------------------------------------------------------------------------------------------------//
        while (orderCount < 4) {
            ordersToSave.addAll(apiOrders.getOrders());
            String nextPage = apiOrders.getLinks().getNext();
            apiOrders = getApiOrders(nextPage);
            orderCount++;
            log.info("Page " + orderCount + " of orders loaded!");
        }
        orderRepository.saveOrders(ordersToSave);
        resume(ordersToSave);
        log.info(orderCount + " Pages of orders were successfully imported!");
    }

    ApiOrders getApiOrders(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(OrderService::parseJson)
                .join();
    }

    private static ApiOrders parseJson(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, ApiOrders.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }

    @Override
    public void exportOrdersIntoCSV(String exportCsvPath) throws SQLException, IOException {
        List<Order> orders = orderRepository.loadAllOrders();
        if (orders != null) {
            List<Order> sortedOrders = getOrdersSortedById(orders);
            log.info("Start writing!");
            writeOrdersToCSV(sortedOrders, exportCsvPath);
            log.info("Orders were successfully exported into " + exportCsvPath + " file!");
        }
    }

    private List<Order> getOrdersSortedById(List<Order> orders) {
        orders.sort(Comparator.comparing(Order::getId));
        return orders;
    }
}
