package com.blogspot.nurkiewicz.rest.book;

import com.blogspot.nurkiewicz.Book;
import com.blogspot.nurkiewicz.BookDao;
import com.blogspot.nurkiewicz.rest.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static java.util.Collections.singletonList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	@Resource
	private BookDao bookDao;

	@RequestMapping(value = "/{id}", method = GET)
	public
	@ResponseBody
	Book read(@PathVariable("id") int id) {
		return bookDao.findOne(id);
	}

	@RequestMapping(method = GET)
	public
	@ResponseBody
	Page<Book> listBooks(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "max", required = false, defaultValue = "20") int max) {
		final org.springframework.data.domain.Page<Book> slice = bookDao.findAll(new PageRequest(page - 1, max, Sort.Direction.ASC, "title"));
		return new Page<Book>(slice.getContent(), page, max, slice.getTotalElements());
	}

	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateBook(@PathVariable("id") int id, @RequestBody Book book) {
	    if (bookDao.exists(id)) {
		    book.setId(id);
		    bookDao.save(book);
	    }
	}

	@RequestMapping(method = POST)
	public ResponseEntity<String> createBook(HttpServletRequest request, @RequestBody Book book) {
		final int id = save(book);

		URI uri = new UriTemplate("{requestUrl}/{id}").expand(request.getRequestURL().toString(), id);
		final HttpHeaders headers = new HttpHeaders();
		headers.put("Location", singletonList(uri.toASCIIString()));
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	private int save(Book book) {
		bookDao.saveAndFlush(book);
		return book.getId();
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable("id") int id) {
		bookDao.delete(id);
	}

}
