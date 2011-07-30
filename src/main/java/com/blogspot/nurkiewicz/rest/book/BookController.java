package com.blogspot.nurkiewicz.rest.book;

import com.blogspot.nurkiewicz.rest.Page;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.blogspot.nurkiewicz.rest.book.Cover.DUST_JACKET;
import static com.blogspot.nurkiewicz.rest.book.Cover.HARDCOVER;
import static com.blogspot.nurkiewicz.rest.book.Cover.PAPERBACK;
import static java.util.Collections.singletonList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	private final AtomicInteger curId = new AtomicInteger();
	private final Map<Integer, Book> books = new ConcurrentSkipListMap<Integer, Book>();

	@PostConstruct
	public void initSampleBookDatabase() {
		save(new Book("A Tale of Two Cities", "Charles Dickens", 1859, true, PAPERBACK));
		save(new Book("The Lord of the Rings", "J. R. R. Tolkien", 1954, true, HARDCOVER));
		save(new Book("The Hobbit", "J. R. R. Tolkien", 1937, true, PAPERBACK));
		save(new Book("红楼梦 (Dream of the Red Chamber)", "Cao Xueqin", 1759, false, HARDCOVER));
		save(new Book("And Then There Were None", "Agatha Christie", 1939, true, PAPERBACK));
		save(new Book("The Lion, the Witch and the Wardrobe", "C. S. Lewis", 1950, true, PAPERBACK));
		save(new Book("She", "H. Rider Haggard", 1887, true, PAPERBACK));
		save(new Book("Le Petit Prince (The Little Prince)", "Antoine de Saint-Exupéry", 1943, true, PAPERBACK));
		save(new Book("The Da Vinci Code", "Dan Brown", 2003, false, PAPERBACK));
		save(new Book("Think and Grow Rich", "Napoleon Hill", 1937, true, PAPERBACK));
		save(new Book("The Catcher in the Rye", "J. D. Salinger", 1951, true, PAPERBACK));
		save(new Book("O Alquimista (The Alchemist)", "Paulo Coelho", 1988, false, PAPERBACK));
		save(new Book("Steps to Christ", "Ellen G. White", 1892, true, PAPERBACK));
		save(new Book("Lolita", "Vladimir Nabokov", 1955, true, PAPERBACK));
		save(new Book("Heidis Lehr- und Wanderjahre (Heidi's Years of Wandering and Learning)", "Johanna Spyri", 1880, true, PAPERBACK));
		save(new Book("The Common Sense Book of Baby and Child Care", "Dr. Benjamin Spock", 1946, true, PAPERBACK));
		save(new Book("Anne of Green Gables", "Lucy Maud Montgomery", 1908, true, PAPERBACK));
		save(new Book("Black Beauty: His Grooms and Companions: The autobiography of a horse", "Anna Sewell", 1877, true, PAPERBACK));
		save(new Book("Il Nome della Rosa (The Name of the Rose)", "Umberto Eco", 1980, true, DUST_JACKET));
		save(new Book("The Hite Report", "Shere Hite", 1976, true, PAPERBACK));
		save(new Book("Charlotte's Web", "E.B. White; illustrated by Garth Williams", 1952, true, PAPERBACK));
		save(new Book("The Tale of Peter Rabbit", "Beatrix Potter", 1902, true, PAPERBACK));
		save(new Book("Harry Potter and the Deathly Hallows", "J. K. Rowling", 2007, true, PAPERBACK));
		save(new Book("Jonathan Livingston Seagull", "Richard Bach", 1970, true, PAPERBACK));
		save(new Book("A Message to Garcia", "Elbert Hubbard", 1899, true, PAPERBACK));
		save(new Book("Angels and Demons", "Dan Brown", 2000, true, PAPERBACK));
		save(new Book("Как закалялась сталь (Kak zakalyalas' stal'; How the Steel Was Tempered)", "Nikolai Ostrovsky", 1932, true, PAPERBACK));
		save(new Book("Война и мир (Voyna i mir; War and Peace)", "Leo Tolstoy", 1869, true, PAPERBACK));
		save(new Book("Le avventure di Pinocchio. Storia di un burattino (The Adventures of Pinocchio)", "Carlo Collodi", 1881, true, PAPERBACK));
		save(new Book("You Can Heal Your Life", "Louise Hay", 1984, true, PAPERBACK));
		save(new Book("Kane and Abel", "Jeffrey Archer", 1979, true, PAPERBACK));
		save(new Book("Het Achterhuis (The Diary of a Young Girl, The Diary of Anne Frank)", "Anne Frank", 1947, true, PAPERBACK));
		save(new Book("In His Steps: What Would Jesus Do?", "Charles M. Sheldon", 1896, true, PAPERBACK));
		save(new Book("To Kill a Mockingbird", "Harper Lee", 1960, true, PAPERBACK));
		save(new Book("Valley of the Dolls", "Jacqueline Susann", 1966, true, PAPERBACK));
		save(new Book("Gone with the Wind", "Margaret Mitchell", 1936, true, PAPERBACK));
		save(new Book("Cien Años de Soledad (One Hundred Years of Solitude)", "Gabriel García Márquez", 1967, true, PAPERBACK));
		save(new Book("The Purpose Driven Life", "Rick Warren", 2002, true, PAPERBACK));
		save(new Book("The Thorn Birds", "Colleen McCullough", 1977, true, PAPERBACK));
		save(new Book("The Revolt of Mamie Stover", "William Bradford Huie", 1951, true, PAPERBACK));
		save(new Book("The Girl with the Dragon Tattoo (original title: Män som hatar kvinnor)", "Stieg Larsson", 2005, true, PAPERBACK));
		save(new Book("The Very Hungry Caterpillar", "Eric Carle", 1969, true, PAPERBACK));
		save(new Book("Sophie's World", "Jostein Gaarder", 1991, true, PAPERBACK));
	}

	@RequestMapping(value = "/{id}", method = GET)
	public
	@ResponseBody
	Book read(@PathVariable("id") int id) {
		return books.get(id);
	}

	@RequestMapping(method = GET)
	public
	@ResponseBody
	Page<Book> listBooks(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "max", required = false, defaultValue = "20") int max) {
		final ArrayList<Book> booksList = new ArrayList<Book>(books.values());
		final int startIdx = (page - 1) * max;
		final int endIdx = Math.min(startIdx + max, books.size());
		return new Page<Book>(booksList.subList(startIdx, endIdx), page, max, books.size());
	}

	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateBook(@PathVariable("id") int id, @RequestBody Book book) {
	    if (books.containsKey(id)) {
		    book.setId(id);
		    books.put(id, book);
	    }
	}

	@RequestMapping(method = POST)
	public ResponseEntity<String> createBook(HttpServletRequest request, @RequestBody Book book) {
		final int id = save(book);

		URI uri = new UriTemplate("{requestUrl}/{username}").expand(request.getRequestURL().toString(), id);
		final HttpHeaders headers = new HttpHeaders();
		headers.put("Location", singletonList(uri.toASCIIString()));
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	private Integer save(Book book) {
		final int id = curId.incrementAndGet();
		book.setId(id);
		books.put(id, book);
		return id;
	}

	@RequestMapping(value = "/{id}", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable("id") int id) {
		books.remove(id);
	}

}
