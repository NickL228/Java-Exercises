package HW_week1_carrental;

public class Car {
    private String plateNumber;
    private String model;
    private int kilometers;
    private boolean isRented;

    public Car(String plateNumber, String model, int kilometers) {
        this.plateNumber = plateNumber;
        this.model = model;
        this.kilometers = kilometers;
        this.isRented = false;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public boolean isRented() {
        return isRented;
    }

    public void rent() {
        isRented = true;
    }

    public void returnCar(int newKilometers) {
        isRented = false;
        this.kilometers = newKilometers;
    }

    public void display() {
        System.out.println("Plate: " + plateNumber + ", Model: " + model + ", Km: " + kilometers + ", Rented: " + isRented);
    }
}