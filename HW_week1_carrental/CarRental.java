package HW_week1_carrental;

import java.util.Scanner;

public class CarRental {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Car[] fleet = new Car[5];
        fleet[0] = new Car("ABC123", "Toyota Corolla", 30000);
        fleet[1] = new Car("DEF456", "Honda Civic", 45000);
        fleet[2] = new Car("GHI789", "Ford Focus", 27000);
        fleet[3] = new Car("JKL012", "BMW 320i", 52000);
        fleet[4] = new Car("MNO345", "Nissan Juke", 39000);

        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Display all cars");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter plate number: ");
                    String plateToRent = scanner.nextLine();
                    boolean foundToRent = false;
                    for (Car car : fleet) {
                        if (car.getPlateNumber().equalsIgnoreCase(plateToRent)) {
                            if (!car.isRented()) {
                                car.rent();
                                System.out.println("Car rented successfully.");
                            } else {
                                System.out.println("Car is not available.");
                            }
                            foundToRent = true;
                            break;
                        }
                    }
                    if (!foundToRent) System.out.println("Car not found.");
                    break;

                case 2:
                    System.out.print("Enter plate number: ");
                    String plateToReturn = scanner.nextLine();
                    boolean foundToReturn = false;
                    for (Car car : fleet) {
                        if (car.getPlateNumber().equalsIgnoreCase(plateToReturn)) {
                            if (car.isRented()) {
                                System.out.print("Enter new kilometers: ");
                                int newKm = scanner.nextInt();
                                car.returnCar(newKm);
                                System.out.println("Car returned and km updated.");
                            } else {
                                System.out.println("Car is not currently rented.");
                            }
                            foundToReturn = true;
                            break;
                        }
                    }
                    if (!foundToReturn) System.out.println("Car not found.");
                    break;

                case 3:
                    System.out.println("All Cars:");
                    for (Car car : fleet) {
                        car.display();
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }
}
