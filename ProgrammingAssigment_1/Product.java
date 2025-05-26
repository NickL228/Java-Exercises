package ProgrammingAssigment_1;

public abstract class Product {
    protected String name;
    protected double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract void displayDetails();
}
