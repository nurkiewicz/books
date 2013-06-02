package com.blogspot.nurkiewicz.rest;

import com.blogspot.nurkiewicz.Book;
import org.springframework.data.domain.Page;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * @author Tomasz Nurkiewicz
 * @since 13.06.11, 16:55
 */
@XmlRootElement
@XmlSeeAlso({Book.class})
public class ViewPage<T> {

	private List<T> rows;

	private int page;
	private int max;
	private long total;

	public ViewPage() {
	}

	public ViewPage(Page<T> dbPage) {
		this.rows = dbPage.getContent();
		this.page = dbPage.getNumber() + 1;
		this.max = dbPage.getSize();
		this.total = dbPage.getTotalElements();
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
