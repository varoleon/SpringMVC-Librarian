package test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import test.dao.DataAccess;
import test.entity.*;

@Controller
public class FirstSpringMVCController {
	@Autowired
	DataAccess dao; // = new DataAccess();

	@RequestMapping(value = "welcome")
	public ModelAndView helloWorld() {

		String message = "<br><div style='text-align:center'>" + "<h3>****Hello world****</h3></div>";

		return new ModelAndView("welcome", "message", message);
	}

	@RequestMapping(value = "/searchByAuthor", method = RequestMethod.GET)
	public String searchForm() {

		return "search";
	}

	@RequestMapping(value = "/searchByAuthor", method = RequestMethod.POST)
	public String search(@RequestParam("author") String author, Model model) {
		System.out.println(author);

		List<Book> books = null;
		if (author != null) {
			books = dao.getBooksByAuthor(author);
		}

		model.addAttribute("books", books);

		return "search";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("isbn") String isbn, Model model) {

		int res = dao.deleteBook(Long.parseLong(isbn));
		String message = (res == 0) ? "Error, no deletion" : "Book Deleted";
		boolean success = (res == 0) ? false : true;

		model.addAttribute("deleteMessage", message);
		model.addAttribute("success", success);
		return "delete";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {

		return "addBook";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam("isbn") String isbn, @RequestParam("bookName") String bookName,
			@RequestParam("author") String author, @RequestParam("price") String price,
			@RequestParam("publication") String publication, @RequestParam("description") String description,
			Model model) {
		String error = "Ooops , something went wrong";
		int res = 0;
		try {
			Long newIsbn = Long.parseLong(isbn);
			int newPrice = Integer.parseInt(price);
			Book b = new Book(bookName, newIsbn, publication, newPrice, description, author);
			System.out.println(b);
			res = dao.addBook(b);

		} catch (Exception e) {
			error = "Error, book not added. Isbn and price must be integers";
		}

		String message = (res == 0) ? error : "Book added succesfully";
		boolean success = (res == 0) ? false : true;

		model.addAttribute("addMessage", message);
		model.addAttribute("success", success);
		return "addBook";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateForm(@RequestParam("isbn") String isbn, Model model) {
		Book b = dao.getBookByIsbn(Long.parseLong(isbn));
		model.addAttribute("book", b);
		return "update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestParam("isbn") String isbn, @RequestParam("bookName") String bookName,
			@RequestParam("author") String author, @RequestParam("price") String price,
			@RequestParam("publication") String publication, @RequestParam("description") String description,
			Model model) {
		
		int res=0;
		String error="Something went wrong";
		try {
			Long newIsbn = Long.parseLong(isbn);
			int newPrice = Integer.parseInt(price);
			res = dao.updateBookByIsbn(bookName, newIsbn, publication, newPrice, description, author);

		} catch (Exception e) {
			error="Error, not updated. Isbn and Price must be integers";
		}

		String message = (res == 0) ? error : "Book udatated succesfully";
		boolean success = (res == 0) ? false : true;

		model.addAttribute("updateMessage", message);
		model.addAttribute("success", success);

		return "search";
	}
}
