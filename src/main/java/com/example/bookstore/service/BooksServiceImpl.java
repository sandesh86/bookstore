package com.example.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dto.BooksDTO;
import com.example.bookstore.entity.Books;
import com.example.bookstore.loader.DataLoader;

@Service
public class BooksServiceImpl implements BooksService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private DataLoader dataLoader;
	
	@Override
	public List<BooksDTO> getAllBooks() {
		List<BooksDTO> booksList = new ArrayList<>();
		bookRepository.findAll().forEach(book -> booksList.add(new BooksDTO(book)));
		return booksList;
	}

	@Override
	public BooksDTO getBookById(Long id) {
		Optional<Books> books = bookRepository.findById(id);
		if (books.isPresent()) {
			return new BooksDTO(books.get());
		}
		return null;
	}

	@Override
	public Long saveOrUpdateBook(BooksDTO booksDTO) {
		Books books = new Books(booksDTO);
		books = bookRepository.save(books);
		return books.getId();
	}

	@Override
	public void delete(Long id) {
		bookRepository.deleteById(id);
	}

	@Override
	public Double getTotalCostOnCheckout(List<Long> bucketList, String promotionalCode) {
		// promotional code can be used to reduce final cost based on code mapping with discounts (not implemented)
		List<Books> booksList = bookRepository.getListOfBooks(bucketList);
		return booksList.stream()
			.map(book -> dataLoader.getDiscountedPrice(book.getType(), book.getPrice()))
			.reduce(0d, (a, b) -> a + b);
	}

}
