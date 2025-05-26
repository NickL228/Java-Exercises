package ProgrammingAssigment_1;

public class Grocery extends Product {
    private double weight;
    private String expirationDate;

    public Grocery(String name, double price, double weight, String expirationDate) {
        super(name, price);
        this.weight = weight;
        this.expirationDate = expirationDate;
    }

    @Override
    public void displayDetails() {
        System.out.println("Grocery - " + name + ", $" + price + ", " + weight + "kg, Expires: " + expirationDate);
    }
}
