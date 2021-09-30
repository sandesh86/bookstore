package com.example.bookstore.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.bookstore.dao.BookRepository;
import com.example.bookstore.dto.BookType;
import com.example.bookstore.entity.Books;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySource("classpath:static/discount.properties")
public class DataLoader {

	@Value("${datafile.location}")
	private String dataFileLocation;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BookRepository bookRepository;
	
	private static volatile boolean isDataLoaded = false;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadMetaData () throws IOException {
		log.info("Loading application data... {]", dataFileLocation);
		if (!isDataLoaded) {
			synchronized (this) {
				if (!isDataLoaded) {
					bookRepository.deleteAll();
					Resource resource = new ClassPathResource(dataFileLocation);
					
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
						reader.lines().skip(1).forEach(line -> {
							log.info(line);
							writeToDB(line);
						});
						isDataLoaded = true;
					}
					catch (IOException e) {
						log.error("Error while loading dummy data", e);
						throw e;
					}					
				}
			}
		}
	}

	private void writeToDB(String line) {
		String [] arr = line.split(",,");
		Books book = new Books();
		book.setName(arr[1]);
		book.setAuthor(arr[2]);
		book.setType(BookType.valueOf(arr[3].toUpperCase()));
		book.setDescription(arr[4]);
		book.setIsbn(arr[5]);
		book.setPrice(Double.parseDouble(arr[6]));
		bookRepository.save(book);
	}
	
	public double getDiscountedPrice(BookType type, double price) {
		String value = env.getProperty(type.value);
		int discount = null != value ? Integer.parseInt(value) : 0;
		log.info("Discount on type: {} is {}%", type.name(), discount);
		return (price - (price * discount)/100);
	}
}
