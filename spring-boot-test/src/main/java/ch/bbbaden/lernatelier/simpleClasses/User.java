package ch.bbbaden.lernatelier.simpleClasses;

import ch.bbbaden.lernatelier.database.JDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class User {

    @Autowired
    JDBCTemplate jdbcTemplate;

    private String firstname;
    private String lastname;
    private String address = "";
    private String zip = "";
    private String city = "";
    private String email;
    private String password;
    private int isVerified;


    private String repeatPassword;

    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(){
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public int getIsVerified(){
        return isVerified;
    }

    public void setIsVerified(int isVerified){
        this.isVerified = isVerified;
    }
}
