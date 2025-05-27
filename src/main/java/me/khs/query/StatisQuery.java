package me.khs.query;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import java.time.*;
import java.time.temporal.*;

// JdbcTemplate : sql 실행을 간편하게 도와주는 spring의 도구 클래스 (jdbc 연결, 예외처리, preparedStatement 등을 내부적으로 처리)

@Repository		//스프링이 이 클래스를 데이터 접근 계층으로 인식하도록 함, 스프링이 자동으로 빈에 등록해줌.
@RequiredArgsConstructor
public class StatisQuery {
	
	private final JdbcTemplate jdbcTemplate;		// sql을 직접 실행할 수 있는 객체 (스프링이 제공)
	LocalDate today = LocalDate.now();
	LocalDate monday = today.with(DayOfWeek.MONDAY); // .with(): 특정 기준에 맞게 날짜를 조정함.
	// today는 클래스가 아니라 LocalDate클래스의 객체(변수 이름), 내부적으로는 TemporlAdjusters.previousOrSame(DayOfWeek.MONDAY(가 자동으로 적용.
	// temporalAdusters는 클래스 : temporalAduster를 생성해주는 정적메서드를 모아둔 유틸 클래스
	LocalDate now = LocalDate.now();
	
	public Long getCurrentWeek(Long userId) {
		
		System.out.println("statisQuery에서 로그 출력 : " + userId);
		String sql = "select sum(total_price) from receipt where user_id = ? AND receipt_date BETWEEN ? AND ? ";
		
		// queryForObject() : sql을 실행하고 결과가 단 하나의 값일 때 사용.
		Long totalPrice = jdbcTemplate.queryForObject(sql, Long.class, userId, monday, now);
		
		return totalPrice != null ? totalPrice : 0L;
		
	}
	
	public Map<String, Integer> getKeywordTotalPrice(Long userId) {
	    Map<String, Integer> keywordTotal = new HashMap<>();
	    String[] keywordNames = {"food", "living", "fashion", "health", "investment", "transportation"};
	    String sql = "SELECT SUM(total_price) FROM receipt WHERE user_id = ? AND keyword_id = ? AND receipt_date BETWEEN ? AND ?";

	    for (int i = 0; i < 6; i++) {
	        Integer price = jdbcTemplate.queryForObject(sql, Integer.class, userId, i + 1, monday, now);
	        keywordTotal.put(keywordNames[i], (price != null) ? price : 0);
	    }

	    return keywordTotal;
	}
	
// LocalDate : 클래스:java.time.LocalDate : 날짜를 표현하는 클래스, year-month-day
// DayOfWeek.MONDAY : java.time.DayOfWeek

	
	
//	public Map<String, Integer> getKeywordTotalPrice(Long userId) {
//		
//		Map<String, Integer> keywordTotal = new HashMap<>();
//		Integer[] keywordPrice = new Integer[6];
//		int price = 0;
//
//		System.out.println("getKeywordTotkaPrice : statisQuery 로그 출력 !");
//		String sql = "select sum(total_price) from reciept where user_id = ? and keyword_id = ? group by keyword_id = ?";
//		
//		for (Long i = 0L; i < 6; i++) {
//			
//			price = jdbcTemplate.queryForObject(sql,  Long.class, userId, i + 1, i+ 1);
//			if (price == 0) keywordPrice[i.intValue()] = 0;
//			else {
//				keywordPrice[i.intValue()] = price;
//			}
//			
//		}
//		
//		keywordTotal.put("food", keywordPrice[1]);
//		keywordTotal.put("living", keywordPrice[2]);
//		keywordTotal.put("fashion", keywordPrice[3]);
//		keywordTotal.put("health", keywordPrice[4]);
//		keywordTotal.put("investment", keywordPrice[5]);
//		keywordTotal.put("transportation", keywordPrice[6]);
//		
//		return keywordTotal;
//		
//	}
	
}
