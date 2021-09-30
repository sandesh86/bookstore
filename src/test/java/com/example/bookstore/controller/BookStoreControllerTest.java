package com.example.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import com.example.bookstore.dto.BookType;
import com.example.bookstore.dto.BooksDTO;
import com.example.bookstore.dto.BucketList;
import com.example.bookstore.service.BooksService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookStoreControllerTest {

	@LocalServerPort
	private int port;
	
	@Mock
	private BooksService booksService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void testGetAllBooks() {
		List<BooksDTO> books = restTemplate.getForObject("http://localhost:" + port + "/books", List.class);
		Assertions.assertEquals(2, books.size());
	}
	
	@Test
	void testGetBooks() {
		BooksDTO books = restTemplate.getForObject("http://localhost:" + port + "/book/1", BooksDTO.class);
		Assertions.assertEquals("My First Book", books.getName());
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
		
		HttpEntity<BooksDTO> entity = new HttpEntity<BooksDTO>(bookDTO);
		
		Long bookId = restTemplate.postForObject("http://localhost:" + port + "/book", entity, Long.class);
		Assertions.assertTrue(bookId > 2);
		
		restTemplate.delete("http://localhost:" + port + "/book/" + bookId);
	}

	@Test
	void testUpdateBookPrice() {
		BooksDTO booksDTO = new BooksDTO();
		booksDTO.setId(1l);
		booksDTO.setName("My First Book");
		booksDTO.setAuthor("Sandesh");
		booksDTO.setDescription("This is fantastic book");
		booksDTO.setIsbn("232-3,22345-09-4");
		booksDTO.setPrice(150);
		booksDTO.setType(BookType.FICTION);
		
		HttpEntity<BooksDTO> entity = new HttpEntity<BooksDTO>(booksDTO);
		
		restTemplate.put("http://localhost:" + port + "/book", entity);
		
		BooksDTO books = restTemplate.getForObject("http://localhost:" + port + "/book/1", BooksDTO.class);
		Assertions.assertEquals(150, books.getPrice());
		
		booksDTO.setPrice(123);
		restTemplate.put("http://localhost:" + port + "/book", entity);
		
		books = restTemplate.getForObject("http://localhost:" + port + "/book/1", BooksDTO.class);
		Assertions.assertEquals(123, books.getPrice());
	}
	
	@Test
	void testGetTotalCostOnCheckout() {
		BucketList bucketList = new BucketList();
		List<Long> list = new ArrayList<>();
		list.add(1l);
		list.add(2l);
		bucketList.setBooksList(list);
		
		HttpEntity<BucketList> entity = new HttpEntity<BucketList>(bucketList);
		
		double value = restTemplate.postForObject("http://localhost:" + port + "/books/checkout", entity, Double.class);
		Assertions.assertEquals(344.7, value);
	}
}
