package me.khs.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import me.khs.dto.StatisDto;

@Service
public class BringReceipt {
	
	private final RestTemplate restTemplate;
	// restTemplate : 스프링에서 rest 요청을 보낼 떄 사용하는 http 클라이언트 도구.
	
	public BringReceipt(RestTemplate restTemplate) {
		
		this.restTemplate = restTemplate;
		
	}
	
	public List<StatisDto>  getReceipt(Long userId) {
		
//		String url = "http://receipt-service_test/getReceipts"; // 뭘 요청한거임?
//		// receipt-service에 요청.
//		ResponseEntity<List<StatisDto>> response = restTemplate.getForEntity(url, StatisDto[].class);
//		// 요청을 보내고 응답 받기
//		// getFor
//		
//		return response;
		
		String url =  "http://receipt-service_test/receipt/getReciept/all";
		// url은 다른 마이크로 서비스의 컨트롤러 엔드인트임. 실제로 이런 경로에 처리할 코드가 있어야 함.
		ResponseEntity<StatisDto[]> response =
	//ResponseEntity<StatisDto[]> 는 ststiaDto 객체 배열을 ResponseEntity로 감싼 형태임. 이 시점에서는 배열이지 아직 리스트는 아님.
						restTemplate.getForEntity(url,  StatisDto[].class);
		// StatisDto[].class : 응답 바디를 어디로 파싱할 지 명시 (여기서는 statisDto 배열로 변환하라는 뜻)
		
		StatisDto[] body = response.getBody();
		
		return body != null ? List.of(body) : List.of();
	
	}
	// RestTemplate이 url 경로로 get요청을 보냄. -> 응답으로 json이 돌아옴. -> restTemplate은 이 json 배열을 statisDto[]배열객체로 자동변환
	// -> 이 배열은 ResponseEntity<StatisDto[]>로 감싸져서 반환됨.
	// 반환할떄는 json -> dto 일때 : json의 key이름과 dto의 필드 이름을 비교해서 매핑함. 키값이 정확하게 일치해야 함.
	
	

}

// ResponseEntity<T> : T는 응답 바디의 타입을 의미, 이 객체는 "http 응답전체"를 나타내는 클래스.
//http 응답에는 상태코드 + 헤더 + 바디가 있음.
//RestTemplate는 http 요청을 수행하기 위한 스프링의 클라이언트 도구임.
// eureka + restTemplate의 조합은 주소는 유레카가 알려주고 요청은 restTemplate이 보내는 구조임.








