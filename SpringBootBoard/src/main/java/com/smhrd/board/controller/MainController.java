package com.smhrd.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.smhrd.board.entity.BoardEntity;

import com.smhrd.board.service.BoardService;

@Controller
public class MainController {

	@Autowired
	BoardService boardService;

//	매핑
	@GetMapping("/")
	public String index(Model model) {
//		board service 연결 -> Autowired코드

//		board service - repository 연결해서 데이터 가지고 오기 	
//		board repository - findAll() --> 새롭게 메서드 만들 필요 X		
//		ArrayList에 담은 후 
//		model 객체에 담아주세요 -> boardList		
		ArrayList<BoardEntity> list = boardService.selectAll();
		model.addAttribute("boardList", list);
		return "index";

	}

// 	로그인 페이지 단순이동
	@GetMapping("/login")
	public String login() {
		return "login";
	}

//  Register(회원가입) 으로 이동할 수 있게
	@GetMapping("/register")
	public String register() {
		return "register";
	}

}
