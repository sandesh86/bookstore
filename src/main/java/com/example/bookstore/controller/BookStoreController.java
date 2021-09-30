package com.example.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.BooksDTO;
import com.example.bookstore.dto.BucketList;
import com.example.bookstore.service.BooksService;

@RestController
public class BookStoreController {

	@Autowired
	private BooksService booksService;
	
	@GetMapping("/books")
	public List<BooksDTO> getAllBooks() {
		return booksService.getAllBooks();
	}
	
	@GetMapping("/book/{bookid}")
	public BooksDTO getBookDetails(@PathVariable("bookid") Long bookid) {
		return booksService.getBookById(bookid);
	}
	
	@DeleteMapping("/book/{bookid}")
	public void deleteBook(@PathVariable("bookid") Long bookid) {
		booksService.delete(bookid);
	}
	
	@PostMapping("/book")
	public Long saveBook (@RequestBody BooksDTO books) {
		return booksService.saveOrUpdateBook(books);
	}
	
	@PutMapping("/book")
	public Long updateBook (@RequestBody BooksDTO books) {
		return booksService.saveOrUpdateBook(books);
	}
	
	@PostMapping("/books/checkout")
	public Double checkout (@RequestBody BucketList bucket) {
		return booksService.getTotalCostOnCheckout(bucket.getBooksList(), bucket.getPromotionalCode());
	}
}
