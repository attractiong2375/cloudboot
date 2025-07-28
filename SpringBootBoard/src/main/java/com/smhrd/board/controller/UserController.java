package com.smhrd.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

// 회원가입 기능
	@Autowired
	private UserService userService;

//	 회원가입 기능
	@PostMapping("/register.do")
	public String register(@RequestParam String id,@RequestParam String pw,
			@RequestParam String name, @RequestParam int age ) {
//	 1. 필요한 것
//		id, pw, age, name 		
	
//	 2. DB 연결 --> Repository , Entity 생성 --> Service 가 가져가버림
//	 3. Service 연결해줘야함
		UserEntity entity = new UserEntity() ;
		entity.setId(id);
		entity.setPw(pw);
		entity.setName(name);
		entity.setAge(age);
		
		
		String result = userService.register(entity);

		if(result.equals("success")) {
			
			return "redirect:/login" ;	
		}else {
			return "redirect:/register" ;
		}
		
		
	}

//	로그인 기능
	@PostMapping("login.do")
	public String login(@RequestParam String id, @RequestParam String pw, HttpSession session) {
//		1. 필요한 거 id, pw
		
//		2. DB 연결 --> service --> repository 연결 --> repository 적절한 메소드 생성(사용) 
		UserEntity entity = new UserEntity() ;
		entity.setId(id) ;
		entity.setPw(pw) ;
	    
		
				
		 
//	   찐2. service 연결	
		UserEntity user = userService.login(id, pw) ;
		
		if (user != null) { // 로그인 성공
			session.setAttribute("user", user) ;
			return "redirect:/" ;
			}else {
				return "redirect:/login" ;
			}
//		로그인이 성공하면 로그인 정보를 저장 ( where ? ) 후 index 페이지로 이동 		
		
		
	}
//		로그아웃 기능
		@GetMapping("/logout")
		public String logout(HttpSession session) {
			session.removeAttribute("user") ;
			
			return "redirect:/" ;
		}
//      write페이지 구현
		@GetMapping("/write") 
		public String write() {
			return "write" ;		
		}
	
}
