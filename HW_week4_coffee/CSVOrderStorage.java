package HW_week4_coffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVOrderStorage implements OrderStorage {
    private static final String FILE_PATH = "orders.csv";

    @Override
    public void saveOrder(Order order) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write(order.toCSV() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }

    @Override
    public List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                orders.add(Order.fromCSV(line));
            }
        } catch (IOException e) {
            // file might not exist — OK
        }
        return orders;
    }
}
