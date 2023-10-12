package es.publicotech.models.interfaces;

import java.io.IOException;
import java.sql.SQLException;

public interface OrderServiceInterface {
    void importOrdersFromCSVIntoDB(String importCsvPath) throws IOException, SQLException;
    void exportOrdersIntoCSV(String exportCsvPath) throws SQLException, IOException;
}
