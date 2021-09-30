package com.example.bookstore.dto;

public enum BookType {

	FICTION("discount.fiction"),
	COMIC("discount.comic"),
	HORROR("discount.horror"),
	FANTASY("discount.fantasy");
	
	public final String value;
	
	private BookType (String type) {
		this.value = type;
	}
}
