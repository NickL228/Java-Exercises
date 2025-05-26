package HW_week4_coffee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLOrderStorage implements OrderStorage {
    private static final String URL = "jdbc:mysql://localhost:3306/coffee_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public MySQLOrderStorage() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "customer VARCHAR(100), " +
                    "amount DOUBLE, " +
                    "date VARCHAR(10))");
        } catch (SQLException e) {
            System.out.println("DB init error: " + e.getMessage());
        }
    }

    @Override
    public void saveOrder(Order order) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO orders (customer, amount, date) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, order.getCustomerName());
            ps.setDouble(2, order.getAmount());
            ps.setString(3, order.getDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DB save error: " + e.getMessage());
        }
    }

    @Override
    public List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM orders")) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("customer"),
                        rs.getDouble("amount"),
                        rs.getString("date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("DB read error: " + e.getMessage());
        }
        return orders;
    }
}
