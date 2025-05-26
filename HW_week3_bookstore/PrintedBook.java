package HW_week3_bookstore;

public class PrintedBook extends Book {
    private boolean hardcover;
    private int pages;
    private String publisher;
    private int availableCopies;

    public PrintedBook(String isbn, String title, double price, String author,
                       boolean hardcover, int pages, String publisher, int availableCopies) {
        super(isbn, title, price, author);
        this.hardcover = hardcover;
        this.pages = pages;
        this.publisher = publisher;
        this.availableCopies = availableCopies;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Printed: " + (hardcover ? "Hardcover" : "Paperback") +
                ", Pages: " + pages +
                ", Publisher: " + publisher +
                ", Available copies: " + availableCopies);
    }
}