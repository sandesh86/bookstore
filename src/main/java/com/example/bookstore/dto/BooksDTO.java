package com.example.bookstore.dto;

import com.example.bookstore.entity.Books;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooksDTO {

	private Long id;
	
	private String name;
	
	private String author;
	
	private String description;
	
	private double price;
	
	private String isbn;
	
	private BookType type;
	
	public BooksDTO() {}
	
	public BooksDTO (Books books) {
		this.id = books.getId();
		this.name = books.getName();
		this.author = books.getAuthor();
		this.description = books.getDescription();
		this.price = books.getPrice();
		this.isbn = books.getIsbn();
		this.type = books.getType();
	}
}
