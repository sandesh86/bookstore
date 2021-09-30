package com.example.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dto.BookType;
import com.example.bookstore.dto.BooksDTO;
import com.example.bookstore.entity.Books;

@SpringBootTest
class BooksServiceImplTest {

	@MockBean
	private BookRepository bookRepository;
	
	@Autowired
	private BooksServiceImpl booksService;
	
	@Test
	void testGetAllBooks() {
		List<Books> booksList = new ArrayList<>();
		Books book1 = new Books();
		book1.setName("First");
		booksList.add(book1);
		
		Books book2 = new Books();
		book2.setName("First");
		booksList.add(book2);
		
		Mockito.when(bookRepository.findAll()).thenReturn(booksList);
		List<BooksDTO> books = booksService.getAllBooks();
		Assertions.assertEquals(2, books.size());
	}
	
	@Test
	void testGetBooks() {
		Books book1 = new Books();
		book1.setId(1l);
		book1.setName("My First Book");
		Mockito.when(bookRepository.findById(1l)).thenReturn(Optional.of(book1));
		BooksDTO books = booksService.getBookById(1l);
		Assertions.assertEquals("My First Book", books.getName());
	}
	
	@Test
	void testGetBooksNull() {
		BooksDTO books = booksService.getBookById(5l);
		Assertions.assertNull(books);
	}

	@Test
	void testAddBook() {
		BooksDTO bookDTO = new BooksDTO();
		bookDTO.setName("Sample Book");
		bookDTO.setAuthor("Arthur");
		bookDTO.setDescription("This book is created only for testing book store");
		bookDTO.setIsbn("222-8-4563-58-1");
		bookDTO.setPrice(330);
		bookDTO.setType(BookType.FANTASY);
		
		Books books = new Books();
		books.setId(5l);
		books.setName("Sample Book");
		books.setAuthor("Arthur");
		books.setDescription("This book is created only for testing book store");
		books.setIsbn("222-8-4563-58-1");
		books.setPrice(330);
		books.setType(BookType.FANTASY);
		
		Mockito.when(bookRepository.save(Mockito.any(Books.class))).thenReturn(books);		
		Long bookId = booksService.saveOrUpdateBook(bookDTO);
		Assertions.assertEquals(5l, bookId);
	}
	
	@Test
	void testGetTotalCostOnCheckout() {		
		List<Books> booksList = new ArrayList<>();
		Books book1 = new Books();
		book1.setName("First");
		book1.setPrice(123);
		book1.setType(BookType.FICTION);
		booksList.add(book1);
		
		Books book2 = new Books();
		book2.setName("First");
		book2.setPrice(234);
		book2.setType(BookType.COMIC);
		booksList.add(book2);
		
		List<Long> list = new ArrayList<>();
		list.add(1l);
		list.add(2l);
		
		Mockito.when(bookRepository.getListOfBooks(list)).thenReturn(booksList);
		double value = booksService.getTotalCostOnCheckout(list, null);
		Assertions.assertEquals(344.7, value);
	}
}
