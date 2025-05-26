package HW_week3_bookstore;

public class AudioBook extends Book {
    private int durationMinutes;
    private String narrator;

    public AudioBook(String isbn, String title, double price, String author, int durationMinutes, String narrator) {
        super(isbn, title, price, author);
        this.durationMinutes = durationMinutes;
        this.narrator = narrator;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Audiobook - Duration: " + durationMinutes + " minutes, Narrator: " + narrator);
    }
}
