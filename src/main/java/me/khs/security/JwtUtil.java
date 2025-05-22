package me.khs.security;

import java.nio.charset.StandardCharsets;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// 이 클래스를 만든 이유 : 통계 서비스에서의 컨트롤러가 토큰을 파싱해서 유저서비스를 꺼내서 사용하려고 함 
// -> 그러나 filter, tokenprovider는 유저서비스에 종속되어있음 -> 유저 도메인을 전부 들고와야 할 상황.

public class JwtUtil {
	
	private final String secretKey = "secret";
	
	public Long getUserIdFromToken(String token) {
		
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
							.build()
							.parseClaimsJws(token)
							.getBody();
		
		return claims.get("userId", Long.class);
		
	}

}
