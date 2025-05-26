package HW_week3_bookstore;

public class Book {
    protected String isbn;
    protected String title;
    protected double price;
    protected String author;

    public Book(String isbn, String title, double price, String author) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.author = author;
    }

    public void displayInfo() {
        System.out.println("ISBN: " + isbn + ", Title: " + title + ", Price: " + price + ", Author: " + author);
    }
}