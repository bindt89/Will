package com.will.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.NoArgsConstructor;


@Controller
@NoArgsConstructor
public class guidanceController {

	
    @GetMapping("/guidance")
    public String guidance() {
        return "guidance/guidance.html";
    }
    

    
    
    
}
