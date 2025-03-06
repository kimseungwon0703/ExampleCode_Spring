package com.example.demo.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<questionEntity, Integer> {

	//findBySubject
	questionEntity findBySubject(String subjcet);
	//findBySubjectAndContent
	questionEntity findBySubjectAndContent(String subject, String content);
	//findBySubjectLike메서드
	List<questionEntity> findBySubjectLike(String subject);
	//페이징 기능을 이용(스프링 프레임 워크의 패키지 클래스 이용)
	Page<questionEntity> findAll(Pageable pageable);
}
