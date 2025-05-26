package HW_week3_bookstore;

public class BookStore {
    public static void main(String[] args) {
        Book[] books = new Book[5];

        books[0] = new PrintedBook("978-123456", "Java Basics", 24.99, "Alice Smith", true, 300, "TechPress", 12);
        books[1] = new PrintedBook("978-789012", "OOP in Practice", 19.99, "John Doe", false, 220, "CodeBooks", 7);
        books[2] = new DigitalBook("978-345678", "Learning Python", 9.99, "Maria Lopez", 1500);
        books[3] = new AudioBook("978-654321", "Design Patterns", 14.99, "Emily Brown", 85, "David Tennant");
        books[4] = new DigitalBook("978-222222", "Web Dev Guide", 7.49, "Liam King", 800);

        for (Book book : books) {
            System.out.println("--------------------------------");
            book.displayInfo();
        }
    }
}
