package com.smhrd.board.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

//	  게시글이 역순으로 출력 될 수 있는 기능
//	  select * from board_entity order by writerDay desc
	ArrayList<BoardEntity> findAllByOrderByWriteDayDesc() ;
	
// like문 사용해서 title을 가지고 오는 메소드
// containing --> like문 (매개변수 안에 있는 값을 바탕으로)	
	List<BoardEntity> findByTitleContaining(String keyword) ;
	
// 내가 원하는 쿼리문 바로 실행 -> @Query
// like문 사용하여 content 가지고 오는 메소드
// : 변수 --> 매개변수로 가지고 온 값을 사용하겠다	
// @Query 메소드 명은 자유롭게 지으셔도 무관 	
   @Query("SELECT b FROM BoardEntity b WHERE b.content LIKE %:keyword% ") 
   List<BoardEntity> searchContent(@Param("keyword") String keyword) ;
   
}
