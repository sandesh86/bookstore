package com.example.bookstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookstore.entity.Books;

public interface BookRepository extends JpaRepository<Books, Long>{

	@Query("SELECT b from Books b WHERE b.id IN (:bookIds)")
	List<Books> getListOfBooks (@Param("bookIds") List<Long> bookIds);
}
