package com.will.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.will.dto.LawyerDto;

import com.will.service.LawyerService;

import lombok.AllArgsConstructor;





@Controller
@AllArgsConstructor
public class LawyerController {
	  private LawyerService lawyerService;
	
	
	    
	    
	    // 회원가입 페이지
	    @GetMapping("/user/signup3")
	    public String dispSignup3() {
	        return "logintool/signup3";
	    }

	    // 회원가입 처리
	    @PostMapping("/user/signup1")
	    public String execSignup(LawyerDto lawyerDto) {
	        lawyerService.joinUser(lawyerDto);

	        return "redirect:/user/login";
	    }
	    
	    // 로그인 페이지
	    @GetMapping("/user/login1")
	    public String dispLogin1() {
	        return "logintool/login";
	    }
	  
	    // 로그인 결과 페이지
	    @GetMapping("/user/login/result1")
	    public String dispLoginResult1() {
	        return "logintool/loginSuccess2";
	    }
}
