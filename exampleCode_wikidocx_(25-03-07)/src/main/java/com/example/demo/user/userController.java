package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
@RequestMapping("/user")
public class userController {

	private final userService UserSer;
	
	public userController(userService UserSer) {
		this.UserSer = UserSer;
	}
	
	@GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, Model model) {

	    if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
	    	model.addAttribute("message", "패스워드 불일치");
            model.addAttribute("PWError", "/user/signup");
	        return "message"; // 비밀번호 불일치 시 폼을 다시 렌더링
	    }

	    try {
	    	UserSer.create(userCreateForm.getUsername(), 
	    			userCreateForm.getEmail(), userCreateForm.getPassword1());	    	
	    }catch(DataIntegrityViolationException e){
	    	e.printStackTrace();
	    	model.addAttribute("message", "이미 등록된 사용자입니다.");
	    	model.addAttribute("PWError", "/user/signup");
	    	return "message";
	    }catch(Exception e) {
	    	e.printStackTrace()	;
	    	model.addAttribute("message", e.getMessage());
	    	model.addAttribute("PWError", "/user/signup");
	    	return "message";
	    }

        return "redirect:/user/signup";
    }
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	/*
	 * @PostMapping("/login") public String loginsite() { return
	 * "redirect:/question/list"; }
	 */
}
