package ch.bbbaden.lernatelier.simpleClasses;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Item {

    private int id;
    private String name;
    private String description;
    private float price;
    private String priceAsString;
    private String code;
    private String length;
    private String fileType;
    private String codeText;

    public Item() {

    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public String getPriceAsString() {
        return priceAsString;
    }

    public void setPriceAsString(float price) {
        this.priceAsString = new DecimalFormat("#0.00").format(price);

    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
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
