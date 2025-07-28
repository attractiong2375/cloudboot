package com.smhrd.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data // getter / setter 합쳐 놓은 것 
@Table(name="user") // 이미 만들어 놓은 DB사용 할 수 없나요?, DB이름을 다르게 지정하고 싶다면
//Table 어노테이션에 name = ~~로 쓰기
public class UserEntity {

//	pk 값이 필수
	@Id   // 해당 컬럼을 pk로 설정하겠다
	@GeneratedValue(strategy=GenerationType.IDENTITY) //auto_increase
	private Long idx; // 객체타입으로 삽입 --> DB에 해당 값이 없으면 null로 값이 넘어옴
	// 기본타입(long, int)은 null 값 없고 기본값이 0이다.
	
	@Column(nullable = false, unique= true)
	private String id ;
	private String pw ;
	private String name ;
	private Integer age ;
}
