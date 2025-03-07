package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;
import com.example.demo.user.userEntity;

@Service
public class questionService {
	
	private final QuestionRepo QueRepo;
	
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
	
	public Page<questionEntity> getList(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.QueRepo.findAll(pageable);
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
