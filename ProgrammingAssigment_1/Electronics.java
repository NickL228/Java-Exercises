package ProgrammingAssigment_1;

public class Electronics extends Product {
    private String brandName;
    private int warrantyPeriod;

    public Electronics(String name, double price, String brandName, int warrantyPeriod) {
        super(name, price);
        this.brandName = brandName;
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public void displayDetails() {
        System.out.println("Electronics - " + name + ", $" + price + ", Brand: " + brandName + ", Warranty: " + warrantyPeriod + " months");
    }
}
