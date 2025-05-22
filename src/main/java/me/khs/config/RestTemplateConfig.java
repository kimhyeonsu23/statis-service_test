package me.khs.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Bean
	@LoadBalanced	// 유레카의 서비스 이름을 인식하게 해줌.
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
		
	}

}
// RestTemplate : http 통신을 위한 도구로 외부 도메인에서 데이터를 가져오거나 전송할 때 사용되는 스프링 프레임워크의 클래스
//  => 동기식 방식으로 요청을 보내고 응답을 받을 때까지 블로킹됨.

// @LoadBalanced : restTemplate 또는 webClient 객체에 부여되어 서비스 간 통신에서 로드밸런싱이 가능하도록 설정.
// 1 . 유레카 같은 서비스 레지스트리를 사용하면 서비스가 등록될 때 이름으로 접근할 수 있음.
// => 서비스 이름을 기반으로 여러 인스턴스 중 하나를 선택해 요청을 라우팅 함.