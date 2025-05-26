package HW_week4_coffee;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CoffeeShopApp {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        OrderStorage storage;

        System.out.print("Use CSV or MySQL for storage? (csv/mysql): ");
        String choice = scanner.nextLine().toLowerCase();
        if (choice.equals("mysql")) {
            storage = new MySQLOrderStorage();
        } else {
            storage = new CSVOrderStorage();
        }

        int option;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add Order");
            System.out.println("2. View orders by date");
            System.out.println("3. View total by customer");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.print("Customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Amount paid: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Date (yyyy/mm/dd): ");
                    String date = scanner.nextLine();
                    storage.saveOrder(new Order(name, amount, date));
                    System.out.println("Order saved.");
                    break;

                case 2:
                    System.out.print("Enter date (yyyy/mm/dd): ");
                    String searchDate = scanner.nextLine();
                    List<Order> byDate = storage.loadOrders();
                    for (Order o : byDate) {
                        if (o.getDate().equals(searchDate)) {
                            System.out.println("- " + o.getCustomerName() + ": €" + o.getAmount());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter customer name: ");
                    String searchName = scanner.nextLine();
                    double total = 0;
                    List<Order> byCustomer = storage.loadOrders();
                    for (Order o : byCustomer) {
                        if (o.getCustomerName().equalsIgnoreCase(searchName)) {
                            total += o.getAmount();
                        }
                    }
                    System.out.println("Total paid by " + searchName + ": €" + total);
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);
    }
}
