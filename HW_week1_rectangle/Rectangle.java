package HW_week1_rectangle;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void draw() {
        for (int i = 0; i < width; i++) {
            System.out.print("* ");
        }
        System.out.println();

        for (int i = 0; i < height - 2; i++) {
            System.out.print("* ");
            for (int j = 0; j < width - 2; j++) {
                System.out.print("  ");
            }
            System.out.println("* ");
        }

        if (height > 1) {
            for (int i = 0; i < width; i++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}