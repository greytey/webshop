package ch.bbbaden.lernatelier.database;

import ch.bbbaden.lernatelier.simpleClasses.Item;
import ch.bbbaden.lernatelier.rowmapper.ItemsRowMapper;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTableIfNotExists() throws Exception {

        log.info("Creating tables");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS item(" +
                "id SERIAL, productname VARCHAR(255), description BLOB, price FLOAT, code VARCHAR(255), length VARCHAR(255));");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user(" +
                "id SERIAL, username VARCHAR(100), password VARCHAR(100));");
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

    private boolean doesUserExist(String username){
        for (User user: users){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void registerNewUser(String username, String password, String repeatPassword){
        if (!doesUserExist(username)){
            if (password == repeatPassword){
                jdbcTemplate.execute("INSERT INTO TABLE user (username, password) VALUES(\""+ username + "\",  \"" + password + "\");");
                getUsers();
                login(username, password);
            }
        }
    }

    private List<User> getUsers(){
        itemsList = jdbcTemplate.query("SELECT * FROM user", new ItemsRowMapper());

        return users;
    }

    private boolean loginCheck(String username, String password){
        for (User user: users){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    private boolean passwordPolicy(){
        return true;
    }

    public boolean login(String username, String password){
        if (doesUserExist(username)){
            if (passwordPolicy()){
                if(loginCheck(username, password)){
                    return true;
                }
            }
        }
        return false;
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
