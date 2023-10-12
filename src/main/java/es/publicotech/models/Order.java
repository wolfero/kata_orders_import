package es.publicotech.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private String orderPriority;
    private LocalDate orderDate;
    private String region;
    private String country;
    private String itemType;
    private String salesChannel;
    private LocalDate shipDate;
    private int unitsSold;
    private double unitPrice;
    private double unitCost;
    private double totalRevenue;
    private double totalCost;
    private double totalProfit;
}

