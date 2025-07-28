package com.smhrd.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.repository.UserRepository;

@Service
public class UserService {
	/*
	 * Spring Controller - Service - Repository(Mapper) 3계층 구조로 구성
	 * 
	 * Controller : 클라이언트의 요청을 처리하고 사용자 인터페이스(UI)와 직접적으로 상호작용 Service : 비지니스 로직, 여러
	 * 개의 Repository와 조합할 수 있음 Repository : DB와 직접 통신
	 * 
	 * Service 객체를 두는 이유 1. Controller가 Repository와 직접 연결하게 되면 가독성이 떨어짐 2.
	 * Cotnroller의 역할 때문에 비슷한 코드가 중복될 가능성이 높음. ㄴ직접 연결 시 변경이 일어나면 다수의 수정이 요구 ==> 유지보수
	 * 향상을 위해 서비스 객체 생성
	 * 
	 */

// 서비스 객체 사용법
// 1. 어노테이션 달아주기
// 2. DB 연결 -- repository interface 객체 생성 X => autowired 생성

	@Autowired
	UserRepository userRepository;

// 3. 기능구현을 위한 메소드 생성
// 회원가입 기능
	public String register(UserEntity entity) {
		// entity
		UserEntity e = userRepository.save(entity);

		if (e != null) {
			// 회원가입 성공
			return "success";
		} else {
			return "fail";
		}
	}

//	아이디 중복체크 기능
	public boolean check(String id) {
		return userRepository.existsById(id);

	}

//	로그인 기능
	public UserEntity login(String id, String pw) {
		
		return userRepository.findAllByIdAndPw(id, pw) ;
		
		
	}
}
