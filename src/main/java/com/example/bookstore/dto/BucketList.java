package com.example.bookstore.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketList {
	
	private List<Long> booksList;
	private String promotionalCode;
}
