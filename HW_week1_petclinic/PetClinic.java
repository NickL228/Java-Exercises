package HW_week1_petclinic;

import java.util.Scanner;

public class PetClinic {
    public static void main(String[] args) {
        Pet[] pets = new Pet[10];
        pets[0] = new Pet("dog", "Pluto");
        pets[1] = new Pet("cat", "Milo");
        pets[2] = new Pet("dog", "Jack");
        pets[3] = new Pet("duck", "Donald");
        pets[4] = new Pet("dog", "Milou");
        pets[5] = new Pet("rabbit", "Thumper");
        pets[6] = new Pet("turtle", "Leonardo");
        pets[7] = new Pet("cat", "Whiskers");
        pets[8] = new Pet("duck", "Quack");
        pets[9] = new Pet("rabbit", "Lola");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Give type: ");
        String searchType = scanner.nextLine().toLowerCase();

        int count = 0;
        System.out.println("Pets of type \"" + searchType + "\":");
        for (Pet pet : pets) {
            if (pet.getType().equalsIgnoreCase(searchType)) {
                System.out.println("- " + pet.getName());
                count++;
            }
        }

        System.out.println("Total animals of " + searchType + " type: " + count);
    }
}