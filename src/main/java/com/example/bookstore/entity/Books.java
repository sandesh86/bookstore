package com.example.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.bookstore.dto.BookType;
import com.example.bookstore.dto.BooksDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Books")
public class Books {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String author;
	
	@Column
	private String description;
	
	@Column
	private double price;
	
	@Column
	private String isbn;
	
	@Column
	private BookType type;
	
	public Books() {}
	
	public Books (BooksDTO booksDTO) {
		this.id = booksDTO.getId();
		this.name = booksDTO.getName();
		this.author = booksDTO.getAuthor();
		this.description = booksDTO.getDescription();
		this.price = booksDTO.getPrice();
		this.isbn = booksDTO.getIsbn();
		this.type = booksDTO.getType();
	}
}
