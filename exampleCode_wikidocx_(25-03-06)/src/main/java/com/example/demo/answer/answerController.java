package com.example.demo.answer;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.question.questionEntity;
import com.example.demo.question.questionService;
import com.example.demo.user.userEntity;
import com.example.demo.user.userService;

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
}