package ch.bbbaden.lernatelier.simpleClasses;

public class Item {

    private int id;
    private String name;
    private String description;
    private float price;
    private String code;
    private String length;

    public Item() {

    }

    public Item(String name, String description, float price, String code, String length) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.code = code;
        this.length = length;
    }

    public Item(String name, String description, float price, String code, String length, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.code = code;
        this.length = length;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", code='" + code + '\'' +
                ", length='" + length + '\'' +
                ", id=" + id +
                '}';
    }
}
