package ProgrammingAssigment_1;

import java.util.ArrayList;
import java.util.Scanner;

public class StoreApp {
    private static ArrayList<Product> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n--- Store Inventory Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Search Product by Name");
            System.out.println("3. Modify Product Price");
            System.out.println("4. Display All Products");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addProduct(); break;
                case "2": searchProduct(); break;
                case "3": modifyProduct(); break;
                case "4": displayAll(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void addProduct() {
        System.out.println("Select category: 1) Electronics  2) Clothing  3) Grocery");
        String cat = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        switch (cat) {
            case "1":
                System.out.print("Brand name: ");
                String brand = scanner.nextLine();
                System.out.print("Warranty period (months): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                inventory.add(new Electronics(name, price, brand, warranty));
                break;

            case "2":
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Material: ");
                String material = scanner.nextLine();
                System.out.print("Color: ");
                String color = scanner.nextLine();
                inventory.add(new Clothing(name, price, size, material, color));
                break;

            case "3":
                System.out.print("Weight (kg): ");
                double weight = Double.parseDouble(scanner.nextLine());
                System.out.print("Expiration date (YYYY-MM-DD): ");
                String expiry = scanner.nextLine();
                inventory.add(new Grocery(name, price, weight, expiry));
                break;

            default:
                System.out.println("Invalid category.");
        }
    }

    private static void searchProduct() {
        System.out.print("Enter product name to search: ");
        String searchName = scanner.nextLine();
        boolean found = false;
        for (Product p : inventory) {
            if (p.getName().equalsIgnoreCase(searchName)) {
                p.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    private static void modifyProduct() {
        System.out.print("Enter product name to modify price: ");
        String searchName = scanner.nextLine();
        for (Product p : inventory) {
            if (p.getName().equalsIgnoreCase(searchName)) {
                System.out.print("Enter new price: ");
                double newPrice = Double.parseDouble(scanner.nextLine());
                p.setPrice(newPrice);
                System.out.println("Price updated.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void displayAll() {
        if (inventory.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            for (Product p : inventory) {
                p.displayDetails();
            }
        }
    }
}
