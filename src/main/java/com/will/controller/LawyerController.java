package com.will.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.will.domain.LawyerDetail;
import com.will.domain.entity.LawyerEntity;
import com.will.domain.repository.LawyerRepository;
import com.will.dto.LawyerDto;
import com.will.service.LawyerService;

import lombok.AllArgsConstructor;





@Controller
@AllArgsConstructor
public class LawyerController {
	  private LawyerService lawyerService;
	  private LawyerRepository lawyerRepository;
	
	
	    
	    
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
	  //현재사용자 정보 가져오기
	    @GetMapping("/lawyer/user/info")
	    public String dispCuurentUserinfo(@AuthenticationPrincipal LawyerDetail lawyerDetail, Model model) {
	    	String id = lawyerDetail.getUsername();
	    	LawyerEntity lawyerEntity = lawyerRepository.findLawyerEntityById(id);
	    	model.addAttribute("currentUser", lawyerEntity);
	    	return "info/myinfo";
	    	
	    }
	    
	  //현재 사용자 정보변경 페이지
	    @GetMapping("/lawyer/resignup/{no}")
	    public String resignup(@AuthenticationPrincipal LawyerDetail lawyerDetail, Model model) {
	        String id  = lawyerDetail.getUsername();
	        LawyerEntity lawyerEntity = lawyerRepository.findLawyerEntityById(id);

	        model.addAttribute("currentUser", lawyerEntity);
	        return "info/myinfo2";
	    }
	    
	    //현재 사용자 정보변경 처리
	    @PostMapping("/lawyer/resignup/update/{no}")
	    public String update(LawyerDto lawyerDto) {
	    	   lawyerService.savePost(lawyerDto);
	    	
	    	return "redirect:/user/info";
	    }
	    
}
