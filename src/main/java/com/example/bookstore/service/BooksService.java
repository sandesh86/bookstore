package com.example.bookstore.service;

import java.util.List;

import com.example.bookstore.dto.BooksDTO;

public interface BooksService {

	List<BooksDTO> getAllBooks();
	
	BooksDTO getBookById(Long id);
	
	Long saveOrUpdateBook(BooksDTO booksDTO);
	
	void delete(Long id);
	
	Double getTotalCostOnCheckout(List<Long> bucketList, String promotionalCode);
}
