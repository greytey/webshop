package ch.bbbaden.lernatelier.database;

import ch.bbbaden.lernatelier.rowmapper.CommentRowMapper;
import ch.bbbaden.lernatelier.rowmapper.UserRowMapper;
import ch.bbbaden.lernatelier.simpleClasses.*;
import ch.bbbaden.lernatelier.rowmapper.ItemsRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class JDBCTemplate implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(JDBCTemplate.class);

    private List<Item> itemsList;
    private List<Item> searchedItemsList;
    private List<User> users;
    private LoginPolicy loginPolicy = new LoginPolicy();

    private List<Comment> comments;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTableIfNotExists() throws Exception {

        log.info("Creating tables");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS item(" +
                "id SERIAL, productname VARCHAR(255), description BLOB, price FLOAT, code VARCHAR(255), fileType VARCHAR(100), codeText BLOB, length VARCHAR(255));");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user(" +
                "id SERIAL, firstname VARCHAR(100), lastname VARCHAR(100), email VARCHAR(100), password VARCHAR(100), address VARCHAR(255), zip VARCHAR(100), city VARCHAR(100), isVerified BIT);");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS comment(" +
                "id SERIAL, displayname VARCHAR(100), createdate VARCHAR(100), commenttext BLOB, productid INT);");
    }

    public List<Item> getAllProducts() {
        itemsList = jdbcTemplate.query("SELECT * FROM item", new ItemsRowMapper());

        return itemsList;
    }

    public List<Comment> getAllComments() {
        comments = jdbcTemplate.query("SELECT * FROM comment", new CommentRowMapper());
        return comments;
    }

    public List<Comment> getAllCommentsForAProduct(int id) {
        getAllComments();
        List<Comment> commentsForAProduct = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getProduct() == id) {
                commentsForAProduct.add(comment);
            }
        }
        return commentsForAProduct;
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
        try {
            searchedItemsList = jdbcTemplate.query("SELECT * FROM item WHERE productname LIKE \"%" + search + "%\"", new ItemsRowMapper());
        } catch (Exception e) {
            searchedItemsList = null;
        }

        return searchedItemsList;
    }

    public boolean registerNewUser(String firstname, String lastname, String email, String password, String repeatPassword) throws Exception {
        if (password.equals(repeatPassword)) {
            if (!doesUserExist(email)) {
                try {
                    if (loginPolicy.loginPolicy(firstname, lastname, email, password)) {
                        String query = "INSERT INTO user (firstname, lastname, email, password) VALUES(?,?,?,? );";
                        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
                            @Override
                            public Boolean doInPreparedStatement(PreparedStatement ps)
                                    throws SQLException, DataAccessException {
                                ps.setString(1, firstname);
                                ps.setString(2, lastname);
                                ps.setString(3, email);
                                ps.setString(4, password);
                                getUsers();
                                return ps.execute();
                            }
                        });
                    }
                } catch (Exception e) {
                    throw new Exception("Login policy failed: " + e.getMessage());
                }
            } else {
                throw new Exception("This email does already exist.");
            }
        } else {
            throw new Exception("The passwords are not the same.");
        }
        return false;
    }

    public boolean login(String email, String password) {
        if (doesUserExist(email)) {
            if (checkLogin(email, password)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLogin(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean doesUserExist(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User getLoggedInUser(String email, String password) {
        List<User> tempUsers = jdbcTemplate.query("SELECT * FROM user WHERE email = \"" + email + "\" AND password = \"" + password + "\";", new UserRowMapper());
        return tempUsers.get(0);
    }

    private List<User> getUsers() {
        users = jdbcTemplate.query("SELECT * FROM user", new UserRowMapper());
        return users;
    }

    public boolean updateUser(User user) {
        final String query = "UPDATE user SET firstname = ?, lastname = ?, address = ?, zip = ?, city = ? WHERE email = ? AND password = ?";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {

                ps.setString(1, user.getFirstname());
                ps.setString(2, user.getLastname());
                ps.setString(3, user.getAddress());
                ps.setString(4, user.getZip());
                ps.setString(5, user.getCity());

                ps.setString(6, user.getEmail());
                ps.setString(7, user.getPassword());
                getUsers();
                return ps.execute();
            }
        });
    }

    public ArrayList<Attachment> getAttachments(ArrayList<String> requestedAttachments){
        ArrayList<Attachment> attachments = new ArrayList();
        for (int i = 0; i< requestedAttachments.size(); i++){
            if (requestedAttachments.get(i).equals(itemsList.get(i).getName())){
                attachments.add(new Attachment(itemsList.get(i).getName(),itemsList.get(i).getCodeText(), itemsList.get(i).getFileType()));
            }
        }
        return attachments;
    }

    public boolean verifyUser(User user){
        if (user.getIsVerified() == 0){
            user.setIsVerified(1);
            final String query = "UPDATE user SET isVerified = ? WHERE email = ? AND password = ?";
            return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
                @Override
                public Boolean doInPreparedStatement(PreparedStatement ps)
                        throws SQLException, DataAccessException {

                    ps.setInt(1, user.getIsVerified());

                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    getUsers();
                    return ps.execute();
                }
            });
        }
        else{
            return false;
        }
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
