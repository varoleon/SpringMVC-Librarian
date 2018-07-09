package test.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import test.entity.*;




@Repository(value="bookDAO")
public class DataAccess {
	
//	  public static DataSource getMysqlDataSource() {
//		    MysqlDataSource dataSource = new MysqlDataSource();
//
//		    // Set dataSource Properties
//		    dataSource.setServerName("localhost");
//		    dataSource.setPortNumber(5000);
//		    dataSource.setDatabaseName("bookdb2");
//		    dataSource.setUser("root");
//		    dataSource.setPassword("admin");
//		    return dataSource;
//		  }
	@Autowired
	JdbcTemplate jdbcTemplate;// = new JdbcTemplate(getMysqlDataSource());
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	public int addBook(Book book) {
		// TODO Auto-generated method stub
		int rows=0;
		String INSERT_BOOK="insert into book values(?, ?, ?, ?, ?, ?)";
		rows = jdbcTemplate.update(INSERT_BOOK,book.getBookName(),book.getISBN(),book.getPublication(),book.getPrice(),
				book.getDescription(),book.getAuthor());
		
		return rows;
	}
	
	public int deleteBook(long isbn) {
		int res=0;
		String DELETE_BOOK="DELETE FROM BOOK WHERE isbn = ?";
		res=jdbcTemplate.update(DELETE_BOOK,isbn);
		return res;
	}
	
	public List<Book> getBooksByAuthor(String author){
		String sql=null;
		ArrayList<Book> books = new ArrayList<Book>();
		List<Map<String, Object>> rows=null;
		
		if (author.equals("")) {
			sql="SELECT * FROM book";
			rows =jdbcTemplate.queryForList(sql);
		}else {
			sql="SELECT * FROM book WHERE author = ?";
			rows =jdbcTemplate.queryForList(sql,author);
		}
		
		
		for (Map<String, Object> row : rows) {
	        Book b=new Book();
	        b.setBookName((String)row.get("bookName"));
	        b.setAuthor((String)row.get("author"));
	        b.setPublication((String)row.get("publication"));
	        b.setDescription((String)row.get("description"));
	        b.setISBN((long)row.get("ISBN"));
	        b.setPrice((int)row.get("price"));
	        
			books.add(b);
	    }   
		
		return books;
	}
	
	public Book getBookByIsbn(long isbn) {
		String sql = "SELECT * FROM book WHERE isbn = ?";
		
		Book b = (Book) jdbcTemplate.queryForObject(sql, new Object[] { isbn }, new BookRowMapper());

		return b;
	}
	
	public int updateBookByIsbn(String bookName, long isbn, String publication, int price,
			String description, String author) {
		int res=0;
		String sql = "UPDATE book SET bookName = ?, publication=?, price=?, description=?, author=? WHERE isbn=?";
		res= jdbcTemplate.update(sql,bookName,publication,price,description,author,isbn);

		return res;
	}
}
