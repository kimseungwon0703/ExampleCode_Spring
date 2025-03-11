package com.example.demo.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepo extends JpaRepository<questionEntity, Integer> {

	//findBySubject
	questionEntity findBySubject(String subjcet);
	//findBySubjectAndContent
	questionEntity findBySubjectAndContent(String subject, String content);
	//findBySubjectLike메서드
	List<questionEntity> findBySubjectLike(String subject);
	//페이징 기능을 이용(스프링 프레임 워크의 패키지 클래스 이용)
	Page<questionEntity> findAll(Pageable pageable);
	//Specification을 통해 조회하기 위함
	Page<questionEntity> findAll(Specification<questionEntity> spec, Pageable pageable);
	
	@Query("select "
            + "distinct q "
            + "from questionEntity q " 
            + "left outer join userEntity u1 on q.author=u1 "
            + "left outer join answerEntity a on a.question=q "
            + "left outer join userEntity u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<questionEntity> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
