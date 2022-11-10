package ch.bbbaden.lernatelier.database;

import ch.bbbaden.lernatelier.simpleClasses.Item;
import ch.bbbaden.lernatelier.rowmapper.ItemsRowMapper;
import ch.bbbaden.lernatelier.simpleClasses.LoginPolicy;
import ch.bbbaden.lernatelier.simpleClasses.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@SpringBootApplication
public class JDBCTemplate implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(JDBCTemplate.class);

    private List<Item> itemsList;
    private List<Item> searchedItemsList;
    private List<User> users;
    private LoginPolicy loginPolicy;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTableIfNotExists() throws Exception {

        log.info("Creating tables");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS item(" +
                "id SERIAL, productname VARCHAR(255), description BLOB, price FLOAT, code VARCHAR(255), length VARCHAR(255));");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user(" +
                "id SERIAL, firstname VARCHAR(100), lastname VARCHAR(100), email VARCHAR(100), password VARCHAR(100));");
    }

    public List<Item> getAllProducts() {
        itemsList = jdbcTemplate.query("SELECT * FROM item", new ItemsRowMapper());

        return itemsList;
    }

    public Item getItemFromId(int id) {
        for (Item item : itemsList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItemsFromSearch(String search) {
        searchedItemsList = jdbcTemplate.query("SELECT * FROM item WHERE productname LIKE \"%" + search + "%\"", new ItemsRowMapper());

        return searchedItemsList;
    }

    public boolean registerNewUser(String firstname, String lastname, String email, String password, String repeatPassword) {
        if (password == repeatPassword) {
            if (!doesUserExist(firstname + " " + lastname)) {
                if (loginPolicy.loginPolicy(firstname, lastname, email, password)){
                    jdbcTemplate.execute("INSERT INTO TABLE user (firstname, lastname, email, password) VALUES(\"" + firstname + "\", \"" + lastname + "\", \"" + lastname + "\", \"" + password + "\");");
                    getUsers();
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean login(String name, String password) {
        if (doesUserExist(name)) {
            if (checkLogin(name, password)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLogin(String name, String password) {
        for (User user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean doesUserExist(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private List<User> getUsers() {
        itemsList = jdbcTemplate.query("SELECT * FROM user", new ItemsRowMapper());
        return users;
    }

    @Override
    public void run(String... args) throws Exception {
        createTableIfNotExists();
        getAllProducts();
        getUsers();
    }


    public static void main(String args[]) {
        SpringApplication.run(JDBCTemplate.class, args);
    }
}
