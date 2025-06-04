package com.budgetmate.schedule;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.budgetmate.dto.UserDto;
import com.budgetmate.query.StatisQuery;

@Component // Schedule 사용할 클래스는 반드시 빈으로 등록되어야 스프링이 인식함.
public class WeeklySchedule {
	
	private final StatisQuery statisQuery;
	
	public WeeklySchedule(StatisQuery statisQuery) {
		
		this.statisQuery = statisQuery;
		
	}
	
	@Scheduled(cron = "0 55 23 ? * SUN")
	public void WeeklyScheduleUpdate() { // CRON의 표현식 : 초 분 시 일 월 요일
			
		updatePoint();
		updateBadge();
				
	}
	
	@Transactional
	public void updatePoint() {
		
		// 유저 목록 조회
        // 이번주 소비 계산
        // 지난주와 비교 → 포인트 증가 여부 판단
        // lastWeek 업데이트
        // DB에 저장
		
		List<UserDto> users = statisQuery.getUserList(); // 유저 목록 조회.
		
		for (UserDto user : users) {
			
			int currentWeek = statisQuery.getCurrentWeek(user.getId()).intValue(); // 이번주 소비 계산
			if (currentWeek > user.getLastWeek()) { //만약 조건 충
				
				user.addPoint();
				
			}
			// 아닐 때 + 현재 값을 저번주 소비로 집어넣어야 함.
			user.setLastWeek(currentWeek);
			
			// 업데이트
			statisQuery.updateUser(currentWeek, user.getPoint(), user.getId());

		}
		
	}
	
	@Transactional
	public void updateBadge() {// 절약초보(10) -> 프로절약러(30) -> 절약(60)
		
		// 유저 목록 조회
        // 포인트 조건 비교
        // badge 부여 여부 판단
        // DB에 저장
		List<UserDto> users = statisQuery.getUserList();  // 유저 목록 조회.
		for (UserDto user : users) {
			
			// 이미 뱃지를 받았다면 그 다음에 받을 수 없도록 해야 함.
			if(user.getPoint() < 10) continue;
			if(user.getPoint() == 10) {  // 절약 초보 뱃지
				if (!statisQuery.searchBadgeHistory(user.getId(), 1L)){//(없다면) 만약 이전에 받은 기록이 없다면 부여 아니면 말음. y()
					// history 넣어야 할것 : 발급일자 granted_date, 뱃지 아이디 = 1, 유저 아이디,시작 일자 (week_start_date)
					//updateHistory() : 여기서 history를 업데이트
					statisQuery.updateHistory(user.getId(),1L);
					
				}
			} else if (user.getPoint() == 30) {
				if (!statisQuery.searchBadgeHistory(user.getId(), 2L)) {
					statisQuery.updateHistory(user.getId(), 2L);

				}
			} else if (user.getPoint() == 50) {
				if (!statisQuery.searchBadgeHistory(user.getId(), 3L)) {
					statisQuery.updateHistory(user.getId(), 3L);

				}
			}
			
		}
		
	}

}
