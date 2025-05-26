package me.khs.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisDto {
	
	private String shop;
	private int totalPrice;
	private String keywordId;
	private LocalDate date;

}