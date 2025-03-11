package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;
import com.example.demo.answer.answerEntity;
import com.example.demo.user.userEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class questionService {
	
	private final QuestionRepo QueRepo;
	
	private Specification<questionEntity> search(String kw){
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<questionEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복제거
				Join<questionEntity, userEntity> u1 = q.join("author", JoinType.LEFT);
				Join<questionEntity, answerEntity> a = q.join("answerList", JoinType.LEFT);
				Join<answerEntity, userEntity> u2 = a.join("author", JoinType.LEFT);
				return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), //제목
						cb.like(q.get("content"), "%" + kw + "%"), //내용
						cb.like(u1.get("username"), "%" + kw + "%"), //질문 작성자
						cb.like(a.get("content"), "%" + kw + "%"), //답변 내용
						cb.like(u2.get("username"), "%" + kw + "%")); //답변 작성자
			}
		};
	}
	
	public questionService(QuestionRepo QueRepo) {
        this.QueRepo = QueRepo;
    }
	
	public List<questionEntity> getList(){
		return this.QueRepo.findAll();
	}
	
	public questionEntity getQuestion(Integer id) {  
        Optional<questionEntity> question = this.QueRepo.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
	
	public void create(String subject, String content, userEntity user) {
		questionEntity q = new questionEntity();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.QueRepo.save(q);
	}
	
	public Page<questionEntity> getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		
		/* Specification<questionEntity> spec = search(kw); */
		
		return this.QueRepo.findAllByKeyword(kw, pageable);
	}
	
	public void modify(questionEntity questionEntity, String subject, String content) {
		questionEntity.setSubject(subject);
		questionEntity.setContent(content);
		questionEntity.setModifyDate(LocalDateTime.now());
		this.QueRepo.save(questionEntity);
	}
	
	public void delete(questionEntity questionEntity) {
		this.QueRepo.delete(questionEntity);
	}
	
	public void vote(questionEntity questionEntity, userEntity userEntity) {
		questionEntity.getVoter().add(userEntity);
		this.QueRepo.save(questionEntity);
	}
}
