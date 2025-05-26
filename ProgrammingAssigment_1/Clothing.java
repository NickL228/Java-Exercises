package ProgrammingAssigment_1;

public class Clothing extends Product {
    private String size;
    private String material;
    private String color;

    public Clothing(String name, double price, String size, String material, String color) {
        super(name, price);
        this.size = size;
        this.material = material;
        this.color = color;
    }

    @Override
    public void displayDetails() {
        System.out.println("Clothing - " + name + ", $" + price + ", Size: " + size + ", Material: " + material + ", Color: " + color);
    }
}
