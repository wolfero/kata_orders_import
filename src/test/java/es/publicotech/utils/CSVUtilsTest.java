package es.publicotech.utils;

import es.publicotech.models.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilsTest {

    @Test
    void shouldReadOrdersFromCSV() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/import/RegistroVentas1.csv");

        List<Order> orders = CSVUtils.readOrdersFromCSV(inputStream);

        int expectedSize=1000;
        assertEquals(expectedSize, orders.size());
    }

}


