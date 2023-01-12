package ch.bbbaden.lernatelier.rowmapper;


import ch.bbbaden.lernatelier.simpleClasses.Item;
import ch.bbbaden.lernatelier.simpleClasses.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setLastname(rs.getString("lastname"));
        user.setFirstname(rs.getString("firstname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAddress(rs.getString("address"));
        user.setZip(rs.getString("zip"));
        user.setCity(rs.getString("city"));
        //user.setVerificationCode(rs.getString("verificationCode"));
        user.setIsVerified((rs.getInt("isVerified")));
        return user;
    }
}
