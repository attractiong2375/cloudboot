package com.smhrd.board.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BoardEntity {
	@Id // PK걸어주는 것
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increase
	private Long id ;
	
	@Column(nullable=false)
	private String title ;
	@Column(nullable=false)
	private String writer ;
	@Column(nullable=false, columnDefinition = "TEXT") // DB컬럼을 text로 바꾼다는 코드
	private String content ; 
	
	private String imgPath ; // 이미지 저장 DB에 이미지 저장 X  서버에 이미지 저장하고 서버 경로를 DB에 저장 
	
//	현재 날짜 ( 글 작성 시간)	
	@Column(nullable=false, updatable = false) // 해당 컬럼은 업데이트 X
	private LocalDate writeDay ;
	
//	JPA 글 작성 시 자동으로 오늘날짜 입력할 수 있게하는 코드 
	@PrePersist
	protected void onWiteDay() {
		this.writeDay = LocalDate.now();
			
	}
}
