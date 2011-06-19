package com.blogspot.nurkiewicz.rest.book;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book implements Comparable<Book> {

	private Integer id;
	private String author;
	private String title;
	private int publishedYear;
	private boolean available;
	private Cover cover;
	private String comments;

	public Book() {
	}

	public Book(String title, String author, int publishedYear, boolean available, Cover cover) {
		this.title = title;
		this.author = author;
		this.publishedYear = publishedYear;
		this.available = available;
		this.cover = cover;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Cover getCover() {
		return cover;
	}

	public void setCover(Cover cover) {
		this.cover = cover;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public int compareTo(Book o) {
		return id > o.id? 1 : id < o.id? -1 : 0;
	}
}