package HW_week3_bookstore;

public class DigitalBook extends Book {
    private int sizeKb;

    public DigitalBook(String isbn, String title, double price, String author, int sizeKb) {
        super(isbn, title, price, author);
        this.sizeKb = sizeKb;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Digital Book - Size: " + sizeKb + " KB");
    }
}