package me.khs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration	// 설정 클래스
@EnableWebFluxSecurity	// spring WebFlux 기반에서 spring security를 활성해줌 -> securityWebFilterChain을 자동 구성하고 보안 필터를 활성화.
public class SecurityConfig {	// Spring Security 요청  전체 흐름 제어 
	
	@Bean 	// spring이 IOC 컨테이너에 등록
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) { // ServerHttpSecurity : spring Security에서 제공하는 webFlux용 안 설정 빌더 클래스
		
		return http
				.csrf(csrf -> csrf.disable())	// CSRF 보호를 끄는 설정. REST API + JWT 기반에서는 인증이 필요없음 -> disable()
				.cors(cors -> cors.disable())	// spring security의 내장 CORS 필터를 끄는 설정 (CorsWebFIlter를 별도로 등록해서 처리할 예정이므로)
				.authorizeExchange(			// 어떤 요청을 허용할지 / 차단할지를 정하는 부분.
						exchange // 람다 매개변수
						-> exchange    // exchange : 경로 조건 설정 객체
						.anyExchange().permitAll()	// 모든 요청을 허용하겠다는 뜻.
						)
				.build();   // 지금까지 작성한 http 설정을 종합해서 securityWebFilterChain객체를 생성함.
								// => 이후 spring Security 필터로 등록되어서 요청마다 작동함.
		
	}
	

}

// spring security 를 쓰면 반드시 모든 요청에 대해 인증을 요구함. securityWebFilterChain에서 cors와  인증을 명시적으로 풀어줘야 함.
// spring security는 필터 체인 앞에 끼어들면서 corsWebFilter보다 먼저 요청을 처리함. 
// 순서 흐름 : 브라우저 요청 -> Spring Security 필터 -> CorsWebFilter -> Controller

// CSRF : 웹 브라우저 기반에서 폼을 통한 요청을 보호하는 기술인데 REST API + JWT 기반 인증에서는 필요없음 -> disable() => 꺼야 하는 이유 : jwt 기반은 폼인증이나 세션쿠키 기반 요청을 안쓰기 때문.


//[브라우저 - Preflight OPTIONS 요청]
//	       ↓
//	[SecurityWebFilterChain (Spring Security)]
//	  - 인증이 필요한 요청인가?
//	  - 허용된 경로인가?
//	  - CSRF 켜져 있는가?
//	  - CORS 필터는 비활성화됐는가?
//	       ↓
//	[CorsWebFilter]
//	  - Origin 허용된 곳인가?
//	  - 메서드 허용되는가? (GET, POST, OPTIONS 등)
//	  - 헤더 허용되는가?
//	       ↓
//	[컨트롤러 (RestController)]