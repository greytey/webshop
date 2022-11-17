package ch.bbbaden.lernatelier.rowmapper;

import ch.bbbaden.lernatelier.simpleClasses.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {

        Comment comment = new Comment();
        comment.setCommenttext(rs.getString("commenttext"));
        comment.setName(rs.getString("displayname"));
        comment.setDate(rs.getString("createdate"));
        comment.setProduct(rs.getInt("productid"));

        return comment;
    }
}
