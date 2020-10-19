package com.will.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


//import com.will.domain.MemberDetail;
import com.will.domain.entity.MemberEntity;
import com.will.domain.repository.MemberRepository;
import com.will.dto.MailDto;
import com.will.dto.MemberDto;
import com.will.service.MemberService;
//import com.will.service.SendEmailService;


import lombok.AllArgsConstructor;





@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;
    private MemberRepository memberRepository;
//    private SendEmailService sendEmailServeice;
//    
    
    // 회원가입  동의하기페이지
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "logintool/signup";
    }
    
    
    // 회원가입 페이지
    @GetMapping("/user/signup2")
    public String dispSignup2() {
        return "logintool/signup2";
    }

    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(MemberDto memberDto) {
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }

    
    
    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "logintool/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "logintool/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "logintool/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "info/myinfo";
    }
    // 내 정보 페이지
    @GetMapping("/user/info2")
    public String dispMyInfo2() {
        return "info/myinfo2";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String Admin() {
        return "/admin";
    }
 
//    //현재사용자 정보 가져오기
//    @GetMapping("/myinfo")
//    public String dispCuurentUserinfo(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
//    	String id = memberDetail.getUsername();
//    	MemberEntity memberEntity = memberRepository.findMemberEntityById(id);
//    	model.addAttribute(currentUser, memberEntity);
//    	return "info/info2";
//    	
//    }
//
//    //현재 사용자 정보 페이지
//    @GetMapping("resignup/{no}")
//    public String resignup(@AuthenticationPrincipal  MemberDetail memberDetail, Model model) {
//    	String id = memberDetail.getUsername();
//    	MemberEntity memberEntity = memberRepository.findMemberEntityById(id);
//    	model.addAttribute(currentUser, memberEntity);
//    	return "info/resignup";
//    }
//     
//    //현재 사용자 비밀번호변경 페이지
//    @GetMapping("repass/{no}")
//    public String resignup(@AuthenticationPrincipal  MemberDetail memberDetail, Model model) {
//    	String id = memberDetail.getUsername();
//    	MemberEntity memberEntity = memberRepository.findMemberEntityById(id);
//    	model.addAttribute(currentUser, memberEntity);
//    	return "info/repass";
//    }
//    
//    //입력한 password와 DB상 password 일치여부를 check하는 컨트롤러
//    @PostMapping("/check/Pw")
//    public @ResponseBody void userPasswordCheck(String currentpw, String id) {
//    	
//    	MemberEntity memberEntity = memberRepository.findMemberEntityById(id);
//    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    	System.out.println("DB 회원의 비밀번호 :" + memberEntity.getPassword());
//    	System.out.println("입력한 비밀번호 : " +String currentpw);
//    	
//    	if(encoder.matches(currentpw, memberEntity.getPassword())) {
//    		System.out.println("비밀번호 일치");
//    	}else {
//    		System.out.println("비밀번호 불일치");
//    	}
//    }
//    
//    //새로 입력한 password로 사용자의 password를 변경하는 컨트롤러
//    @PostMapping("/check/Pw/changPw")
//    @ResponseBody
//    public void changPw(String newpw, String id) {
//    	memberService.updatePassword(newpw, id);
//    }
//    
//    //현재 사용자 정보변경 처리
//    @PostMapping("/resignup/update/{no}")
//    public String update(MemberDto memberDto) {
//    	memberService.savePost(memberDto);
//    	
//    	return "redirect:/myinfo";
//    }
//    
//    //Email과 name의 일치여부를 check하는 컨트롤러
//    @GetMapping("/check/findPw")
//       public @ResponseBody Map<String, Boolean> pw_find(String email, String name){
//           Map<String,Boolean> json = new HashMap<>();
//           boolean pwFindCheck = memberService.userEmailCheck(email, name);
//           System.out.println(pwFindCheck);
//           json.put("check", pwFindCheck);
//           return json;
//       }
//
//    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 password를 변경하는 컨트롤러
//    @PostMapping("/check/findPw/sendEmail")
//    public @ResponseBody void sendEmail(String email, String name){
//       MailDto dto = sendEmailService.createMailAndChangePassword(email, name);
//       sendEmailService.mailSend(dto);
//    }
//    
//    // 어드민 페이지
//    @GetMapping("/admin")
//    public String dispAdmin() {
//        return "admin/admin";
//    }
//    
//    // 어드민 회원명단 페이지
//    @GetMapping("/admin/member")
//    public String list(Model model) {
//        List<MemberDto> memberList = memberService.getMemberlist();
//
//        model.addAttribute("memberList", memberList);
//        return "admin/Member";
//    }
//    
//    @GetMapping("idCheck")
//    @ResponseBody
//    public String email_check(String email) {
//        System.out.println(email);
//        String str = memberService.idCheck(email);
//        return str;
//    }
//    
//  //등록된 이메일로 인증번호를 발송하고 발송된 인증번호로 사용자 이메일로 보내는 컨트롤러
//    @PostMapping("/sendEmail")
//    public void sendEmail(@RequestParam String email){
//        MailDto dto = sendEmailService.createMailAndCheck(email);
//        sendEmailService.mailSend(dto);
//
//    }
//    
//    
}