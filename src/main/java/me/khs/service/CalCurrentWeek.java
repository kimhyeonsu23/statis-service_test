package me.khs.service;

import org.springframework.stereotype.Service;

@Service
public class CalCurrentWeek {

}

// 에러코드 : The type jakarta.servlet.ServletException cannot be resolved. It is indirectly referenced from required type org.springframework.web.filter.OncePerRequestFilter
// 이것은 OncePerRequestFilter를 상속받은 클래스를 사용하고 있는데 내부에서 serveltException 클래스를 찾을 수 없기 때문.
