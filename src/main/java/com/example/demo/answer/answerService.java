package com.example.demo.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;
import com.example.demo.question.questionEntity;
import com.example.demo.user.userEntity;


@Service
public class answerService {

    private final AnswerRepo AnsRepo;
    
    public answerService(AnswerRepo AnsRepo) {
    	this.AnsRepo = AnsRepo;
    }

    public answerEntity create(questionEntity question, String content, userEntity author) {
        answerEntity answer = new answerEntity();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.AnsRepo.save(answer);
        return answer;
    }
    
    public answerEntity getAnser(Integer id) {
    	Optional<answerEntity> answerEntity = this.AnsRepo.findById(id);
    	if(answerEntity.isPresent()) {
    		return answerEntity.get();
    	}else {
    		throw new DataNotFoundException("answer not found");
    	}
    }
    
    public void modify(answerEntity answerEntity, String content) {
    	answerEntity.setContent(content);
    	answerEntity.setModifyDate(LocalDateTime.now());
    	this.AnsRepo.save(answerEntity);
    }
    
    public void delete(answerEntity answerEntity) {
    	this.AnsRepo.delete(answerEntity);
    }
    
    public void vote(answerEntity answerEntity, userEntity userEntity) {
        answerEntity.getVoter().add(userEntity);
        this.AnsRepo.save(answerEntity);
    }
}