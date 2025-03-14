package com.example.demo;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.example.demo.question.QuestionRepo;
import com.example.demo.question.questionEntity;
import com.example.demo.question.questionForm;
import com.example.demo.question.questionService;
import com.example.demo.user.userEntity;
import com.example.demo.user.userService;

import jakarta.validation.Valid;

//책에서 QuestionController로 수정함 2장 07
@RequestMapping("/question")
@Controller
public class MyController {
	
	@Autowired
	private final QuestionRepo QueRepo;
	
	@Autowired
	private final questionService QueSer;
	
	@Autowired
	private final userService UserSer;
	
	
	//생성자 초기화 - 문서참고(템플릿에 질문 데이터 전달히가 - Model 활용)
	public MyController(QuestionRepo QueRepo, questionService queSer, userService UserSer) {
        this.QueRepo = QueRepo;
		this.QueSer = queSer;
		this.UserSer = UserSer;
    }
	
	//localhost:8080으로 접속해도 바로 뜰 수 있게 변경
	@GetMapping("/")
	public String root() {
		return "redirect:/question/list";
	}
	
    @GetMapping("/list")
//    @ResponseBody
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
    	//페이징 기능 questionService 참고
    	Page<questionEntity> paging = this.QueSer.getList(page, kw);
    	model.addAttribute("paging", paging);
    	model.addAttribute("kw", kw);
    	
    	List<questionEntity> questionList = this.QueSer.getList();
    	//model객체에 questionList라는 이름으로 저장한다.
    	model.addAttribute("questionList", questionList);
        return "question_list";
    }
    
    @GetMapping(value = "/detail/{id}")
    public String Detail(Model model, @PathVariable("id") Integer id) {
    	questionEntity question = this.QueSer.getQuestion(id);
        model.addAttribute("question", question);
    	return "question_detail";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(questionForm questionForm) {
    	return"question_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid questionForm questionForm, @RequestParam(value="subject") String subject, @RequestParam(value="content") String content, Principal principal){
    	userEntity user = this.UserSer.getUser(principal.getName());
    	this.QueSer.create(questionForm.getSubject(), questionForm.getContent(), user);
    	return "redirect:/question/list";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(questionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
    	questionEntity question = this.QueSer.getQuestion(id);
    	if(!question.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	questionForm.setSubject(question.getSubject());
    	questionForm.setContent(question.getContent());
    	return "question_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid questionForm questionForm, Principal principal, @PathVariable("id") Integer id) {
    	questionEntity questionEntity = this.QueSer.getQuestion(id);
    	if(!questionEntity.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    	}
    	this.QueSer.modify(questionEntity, questionForm.getSubject(), questionForm.getContent());
    	return String.format("redirect:/question/detail/%s", id);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
    	questionEntity questionEntity = this.QueSer.getQuestion(id);
    	if(!questionEntity.getAuthor().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
    	}
    	this.QueSer.delete(questionEntity);
    	return "redirect:/question/list";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
    	questionEntity questionEntity = this.QueSer.getQuestion(id);
    	userEntity userEntity = this.UserSer.getUser(principal.getName());
    	this.QueSer.vote(questionEntity, userEntity);
    	return String.format("redirect:/question/detail/%s", id);
    }
}
