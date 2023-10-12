package es.publicotech.models.API;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.publicotech.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiOrders {
    private Long page;
    @JsonProperty("content")
    private List<Order> orders;
    private ApiLinks links;
}
