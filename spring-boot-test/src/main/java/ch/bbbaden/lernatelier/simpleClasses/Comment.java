package ch.bbbaden.lernatelier.simpleClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {

    private String name;
    private String date;
    private String commenttext;

    private int product;

    public Comment() {
    }

    public Comment(String commenttext, User user, int productId) {
        this.commenttext = commenttext;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }
}
