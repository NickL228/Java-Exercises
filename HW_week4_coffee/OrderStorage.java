package HW_week4_coffee;

import java.util.List;

public interface OrderStorage {
    void saveOrder(Order order);
    List<Order> loadOrders();
}