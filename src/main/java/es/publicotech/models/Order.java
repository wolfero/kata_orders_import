package es.publicotech.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private UUID uuid;
    private String id;
    private String region;
    private String country;
    @JsonProperty("item_type")
    private String itemType;
    @JsonProperty("sales_channel")
    private String salesChannel;
    private String priority;
    private String date;
    @JsonProperty("ship_date")
    private String shipDate;
    @JsonProperty("units_sold")
    private Long unitsSold;
    @JsonProperty("unit_price")
    private Double unitPrice;
    @JsonProperty("unit_cost")
    private Double unitCost;
    @JsonProperty("total_revenue")
    private Double totalRevenue;
    @JsonProperty("total_cost")
    private Double totalCost;
    @JsonProperty("total_profit")
    private Double totalProfit;
    private String self;
}