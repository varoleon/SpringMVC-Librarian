package test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import test.entity.Book;

public class BookRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book b = new Book();
		b.setBookName(rs.getString("bookName"));
        b.setAuthor(rs.getString("author"));
        b.setPublication(rs.getString("publication"));
        b.setDescription(rs.getString("description"));
        b.setISBN(rs.getLong("ISBN"));
        b.setPrice(rs.getInt("price"));
		return b;
	}
}
