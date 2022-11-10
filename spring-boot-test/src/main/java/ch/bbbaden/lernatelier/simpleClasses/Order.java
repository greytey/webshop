package ch.bbbaden.lernatelier.simpleClasses;

public class Order {

    private User user;
    private String name;
    private String firstname;
    private String address;
    private String email;
    private Cart cart;
    private String zip;
    private String city;
    private boolean checked;

    public void Order(User user){
        this.user = user;
        this.name = user.getLastname();
        this.firstname = user.getFirstname();
        this.email = user.getEmail();
        this.city = user.getCity();
        this.address = user.getAddress();
        this.zip = user.getZip();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public User getUser() {
        return user;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
