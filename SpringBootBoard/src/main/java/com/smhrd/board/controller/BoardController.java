package com.smhrd.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smhrd.board.config.FileUploadConfig;
import com.smhrd.board.config.WebConfig;
import com.smhrd.board.entity.BoardEntity;
import com.smhrd.board.entity.UserEntity;
import com.smhrd.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

// controller의 default mapping 주소 설정
@Controller
@RequestMapping("/board")
public class BoardController {

	private final WebConfig webConfig;

	@Autowired
	BoardService boardService;

	@Autowired
	FileUploadConfig fileUploadConfig;

	BoardController(WebConfig webConfig) {
		this.webConfig = webConfig;
	}

//	게시글 작성 기능
	@PostMapping("/write")
	public String write(@RequestParam String title, @RequestParam String content, HttpSession session,
			@RequestParam MultipartFile image) {
//	 1. 필요한거 ?? input태그에서는 File넘어 오는 중(file을 받아주어야 합니다) 
//	    title, writer, content, imgPath
//			 ㄴ (session)		
//		image처리
		String imgPath = "";
//		이미지를 저장할 경로 - C : upload/		
		String uploadDir = fileUploadConfig.getUploadDir();
		if (!image.isEmpty()) {
//		1. 파일의 이름 설정
//		   uuid : 고유식별자 (중복을 막기위해)		
			String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

//		2. 파일이 저장 될 이름과 경로 설정
			String filePath = Paths.get(uploadDir, fileName).toString();

//		3. 서버에 저장 및 경로 설정
			try {
				image.transferTo(new File(filePath));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//		4. DB에 저장 될 경로 문자열 설정			
			imgPath = "/home/git/uploads/" + fileName;

		}

		BoardEntity board = new BoardEntity();
		board.setTitle(title);
		board.setContent(content);
//		writer 
//				
		UserEntity user = (UserEntity) session.getAttribute("user");
		board.setWriter(user.getId());
		board.setImgPath(imgPath);

//	 2. DB 연결 -> repository 설정완료, service객체 완료, 		
		boardService.write(board);

		return "redirect:/";
	}

//  게시글 상세페이지 이동

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) { // URL에 담긴 값 가지고 오는 방법 @PathVariable
//	 1. 필요한 거 
//	  --> id , 다음 페이지로 값을 보내기 위한 Model(scope 종류 중 Request) 객체
//	 2. service연결		
		Optional<BoardEntity> detail = boardService.detailPage(id);

		model.addAttribute("detail", detail.get());

		System.out.println(detail.get().getImgPath());
//		detail --> Optional타입 --> 우리가 원하는건 BoardEntity 타입 
//		.get()		
		return "detail";
	}

//  게시판 수정 기능 edit.html
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {

//	   1. 필요한 것

//	   2. service 연결			
		Optional<BoardEntity> board = boardService.detailPage(id);

		model.addAttribute("board", board.get());

		return "edit";
	}

//	게시판 수정 기능
	@PostMapping("/update")

	public String update(@RequestParam String title, @RequestParam String content, @RequestParam MultipartFile image,
			@RequestParam String oldImgPath, @RequestParam Long id) {

//	 1. title, content, imgpath	id, oldImgPath
//	 db접근해서 해당 게시글의 정보를 다시 가지고 오겠습니다!!!
		BoardEntity entity = boardService.detailPage(id).get();

//	 File 업로드 경로
		String uploadDir = fileUploadConfig.getUploadDir();
//	 기존 이미지 처리
		if (!image.isEmpty()) { // 새롭게 이미지를 업로드를 했다

//	 기존에 있던 이미지를 삭제
			if (oldImgPath != null && !oldImgPath.isEmpty()) {
				Path oldFilePath = Paths.get(uploadDir, oldImgPath.replace("/uploads/", ""));
				try {
					Files.deleteIfExists(oldFilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
//	 새로운 이미지 저장
			String newFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
			Path newFilePath = Paths.get(uploadDir, newFileName);

			try {
				image.transferTo(newFilePath);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			entity.setImgPath("/uploads/" + newFileName);
		}

//		작성자		
//		BoardEntity entity = new BoardEntity();
		entity.setTitle(title);
		entity.setContent(content);
		
//		update문 실행 -- service에 연결 
		/* JPA에서 save했을 때 insert문이 아닌 update 문이 실행되는 조건
		 * findById()해준 후 save 코드를 실행하면
		 * JPA가 자동으로 update라고 인식을 함
		 * 
		 * 대규모로 update 복잡한 update 는 @Query("sql문 작성")
		 * */
		boardService.write(entity) ;
		return "redirect:/board/detail/"+ id;
	}

}
