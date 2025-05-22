package me.khs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.khs.service.BringReceipt;
import me.khs.service.CalCurrentWeek;

@RestController
public class EurekaRestController {
	
	private final BringReceipt bringReceipt;
	private final CalCurrentWeek calCurrentWeek;
	
	public EurekaRestController (BringReceipt bringReceipt, CalCurrentWeek calCurrentWeek) {
		
		this.bringReceipt =  bringReceipt;
		this.calCurrentWeek =  calCurrentWeek;
		
	}
	
	@GetMapping("getCurrentWeek")
	public int getReceipt(@AuthenticationPrincipal User userDetails) { // 반환값은 다시 프론트에 넘겨줘야 함. json 객체형태.
		// AuthenticationPrincipal 이 내부적으로 SecurityContextHolder.getContext().getAuthentication()/getPrincipal()을 사용해 꺼내줌.
		
		int currentWeek = bringReceipt.getRecipt(userDetails.getUserId());
		
		return currentWeek;
		
	}

}

// responseEntity를 사용하면 http 응답을 세부적으로 조작하고 보낼 수 있음
// http 상태코드(200 ok, 404...), 헤더(authorization, content-type) , 바디(실제 응답 데이터)
