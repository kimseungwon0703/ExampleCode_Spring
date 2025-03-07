package com.example.demo.answer;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.question.questionEntity;
import com.example.demo.question.questionService;
import com.example.demo.user.userEntity;
import com.example.demo.user.userService;

import jakarta.validation.Valid;

@RequestMapping("/answer")
@Controller
public class answerController {

    private final questionService QueSer;
    
    private final answerService AnsSer;
    
    private final userService UserSer;
    
    public answerController(questionService QueSer, answerService AnsSer, userService UserSer) {
		this.QueSer = QueSer;
		this.AnsSer = AnsSer;
		this.UserSer = UserSer;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content,Principal principal) {
        questionEntity question = this.QueSer.getQuestion(id);
        userEntity user = this.UserSer.getUser(principal.getName());
        this.AnsSer.create(question, content, user);
        return String.format("redirect:/question/detail/%s", id);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(answerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
    	answerEntity answerEntity = this.AnsSer.getAnser(id);
    	if(!answerEntity.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	answerForm.setContent(answerEntity.getContent());
    	return "answer_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid answerForm answerForm, @PathVariable("id") Integer id, Principal principal,String anotherSentenceExpression) {
    	answerEntity answerEntity = this.AnsSer.getAnser(id);
    	if(!answerEntity.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	this.AnsSer.modify(answerEntity, answerForm.getContent());
    	//여기는 단순 객체 지향의 특성을 방지하는 문장. 위의 GetMapping과의 차이점을 두기 위해서
    	//PostMapping과 변수갯수와 변수형이 동일하여 String을 추가로 집어넣어 다름을 두었음 String은 아무 뜻 없음.
    	anotherSentenceExpression = "객체지향 중복 방지(단순 학습단계)";
    	System.out.print(anotherSentenceExpression);
    	
    	return String.format("redirect:/question/detail/%s", answerEntity.getQuestion().getId());
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
    	answerEntity answerEntity = this.AnsSer.getAnser(id);
    	if(!answerEntity.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
    	}
    	this.AnsSer.delete(answerEntity);
    	return String.format("redirect:/question/detail/%s", answerEntity.getQuestion().getId());
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
    	answerEntity answerEntity = this.AnsSer.getAnser(id);
    	userEntity userEntity = this.UserSer.getUser(principal.getName());
    	this.AnsSer.vote(answerEntity, userEntity);
    	return String.format("redirect:/question/detail/%s", answerEntity.getQuestion().getId());
    }
}