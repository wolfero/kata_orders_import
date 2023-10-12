package es.publicotech.utils;

import es.publicotech.models.Order;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsoleUtils {
    public static void resume(List<Order> orders) {
        Map<String, Long> countByRegion = generateSummary(orders, Order::getRegion);
        Map<String, Long> countByCountry = generateSummary(orders, Order::getCountry);
        Map<String, Long> countByItemType = generateSummary(orders, Order::getItemType);
        Map<String, Long> countBySalesChannel = generateSummary(orders, Order::getSalesChannel);
        Map<String, Long> countByOrderPriority = generateSummary(orders, Order::getOrderPriority);

        printSummary("Count by Region", countByRegion);
        printSummary("Count by Country", countByCountry);
        printSummary("Count by Item Type", countByItemType);
        printSummary("Count by Sales Channel", countBySalesChannel);
        printSummary("Count by Order Priority", countByOrderPriority);
    }

    private static Map<String, Long> generateSummary(List<Order> orders, Function<Order, String> classifier) {
        return orders.stream()
                .collect(Collectors.groupingBy(classifier, Collectors.counting()));
    }

    private static void printSummary(String title, Map<String, Long> map) {
        System.out.println("------ " + title + " ------");
        map.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.println();
    }
}
