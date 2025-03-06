package com.example.demo.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.question.questionEntity;
import com.example.demo.user.userEntity;


@Service
public class answerService {

    private final AnswerRepo AnsRepo;
    
    public answerService(AnswerRepo AnsRepo) {
    	this.AnsRepo = AnsRepo;
    }

    public void create(questionEntity question, String content, userEntity author) {
        answerEntity answer = new answerEntity();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.AnsRepo.save(answer);
    }
}