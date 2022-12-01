package ch.bbbaden.lernatelier.rowmapper;

import ch.bbbaden.lernatelier.simpleClasses.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ItemsRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {

        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("productname"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getFloat("price"));
        item.setCode(rs.getString("code"));
        item.setLength(rs.getString("length"));
        item.setFileType(rs.getString("fileType"));
        item.setCodeText(rs.getString("codeText"));
        item.setPriceAsString(rs.getFloat("price"));
        return item;
    }
}
