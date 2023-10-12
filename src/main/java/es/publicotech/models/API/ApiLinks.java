package es.publicotech.models.API;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiLinks {
    private String next;
    private String prev;
    private String self;
}
