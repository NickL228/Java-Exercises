package HW_week1_petclinic;

public class Pet {
    private String type;
    private String name;

    public Pet(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}