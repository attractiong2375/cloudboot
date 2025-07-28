package com.smhrd.board.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.board.service.UserService;

@RestController
public class UserRestController {

// 	RestController 란?(비동기통신)
//	Controller 역할을 하지만 HTML이 아닌 데이터(Json)을 보내주는 컨트롤러
	
	@Autowired
	UserService userService ;
	
	@GetMapping("/user/check-id")
	public HashMap<String, Boolean> checkId(@RequestParam String id) {
//		 1. 필요한 거 --> id 
//		 2. DB 연결 -> Service -> Service 연결
		boolean exist = userService.check(id);
		
//		Map 구조로 만들어서 Json화 시켜 데이터 전송
//		Map == python dict K :V
//		<K의 자료형, V의 자료형>	 제너릭  ★★<들어가는 것은 레퍼런스 타입이다 >★★ 	
		HashMap<String, Boolean> res= new HashMap<>();
		
//		map에 데이터 넣는 법
//		arraylist --> .add()
//		map -> put
		res.put("exist", exist) ;
		
		return res;
		
		
	}
}
