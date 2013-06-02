package com.blogspot.nurkiewicz;

import com.blogspot.nurkiewicz.rest.Cover;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/2/13, 5:06 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SpringSmokeTest {

	@Resource
	private BookDao bookDao;

	@Test
	public void listing() throws Exception {
		final List<Book> books = bookDao.findAll(new Sort(Sort.Direction.ASC, "title"));
		assertThat(books).isNotEmpty();
	}

	@Test
	public void creating() throws Exception {
		bookDao.save(new Book("Foo", "Bar", 2000, true, Cover.HARDCOVER));
	}

}
