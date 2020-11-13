package com.will.controller;


   import com.will.dto.FileDto;
import com.will.dto.MemberDto;
import com.will.dto.QnAFileDto;
import com.will.dto.WillDto;
import com.will.service.WillService;
import com.will.util.MD5Generator;
import com.will.domain.entity.FileEntity;
import com.will.domain.entity.MemberEntity;
import com.will.domain.repository.MemberRepository;

import lombok.AllArgsConstructor;
import com.will.service.FileService;
import com.will.service.MemberService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
   

   @Controller
   @AllArgsConstructor
   public class WillController {
      
	  private MemberService memberService;
      private WillService willService;
      private FileService fileService;

   
      //유언장 리스트   
       @GetMapping("/user/will/list")
       public String displist(Model model,@RequestParam(value = "page" , defaultValue="1")Integer pageNum) {
           List<WillDto> WillDtoList = willService.getWillList(pageNum);
           Integer[] pageList = willService.getPageList(pageNum);
           
            model.addAttribute("WillList", WillDtoList);
            model.addAttribute("pageList", pageList);
            
           return "will/list";
       }

       //유언장 작성 페이지
       @GetMapping("/user/createwill")
       public String dispcreatewill() {
           return "will/createwill";
       }
       //유언장 db입력
       @PostMapping("/user/createwill")
       public String createwill(@RequestParam("file") MultipartFile files, WillDto WillDto) {
           try {
        	   if(!files.getOriginalFilename().isEmpty())
        	   {
	                String origFilename = files.getOriginalFilename();
	                String filename = new MD5Generator(origFilename).toString();
	                /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
	                String savePath = System.getProperty("user.dir") + "\\files";
	                /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
	                if (!new File(savePath).exists()) {
	                    try{
	                        new File(savePath).mkdir();
	                    }
	                    catch(Exception e){
	                        e.getStackTrace();
	                    }
	                }
	                String filePath = savePath + "\\" + filename;
	                files.transferTo(new File(filePath));
	                FileDto fileDto = new FileDto();
	                fileDto.setOrigFilename(origFilename);
	                fileDto.setFilename(filename);
	                fileDto.setFilePath(filePath);
	                Long fileId = fileService.saveFile(fileDto);
	                WillDto.setFileId(fileId);
        	   }
        	    String Content = WillDto.getContent();
        	    String hashcontent = new MD5Generator(Content).toString();
        	    
        	    WillDto.setHashcontent(hashcontent);
        	   
        	    
        	    if(WillDto.getJinhang() == null)
        	    {
        	    WillDto.setJinhang("수정중");
        	    }
         	  
                WillService.savePost(WillDto);
                
                
                
           } catch(Exception e) {
                e.printStackTrace();
            }
           return "redirect:/user/will/list";
           
     
       }
       
       //디테일
       @GetMapping("/createwill/{no}")
       public String detail(@PathVariable("no") Long no, Model model) {
           WillDto willDto = willService.getPost(no);
           FileDto FileDto;
           
    	   try {
    		   long id = willDto.getFileId();
    		   FileDto = fileService.getFile(id);
    	   }
    	   catch(Exception e) {
    		   FileDto= new FileDto((long) 0,"파일없음","파일없음","");
    	   }
           model.addAttribute("post", willDto);
           model.addAttribute("file", FileDto);
           return "will/detail";
       }
       //유언장 수정 페이지
       @GetMapping("/createwill/edit/{no}")
       public String edit(@PathVariable("no") Long no, Model model) {
           WillDto willDto = willService.getPost(no);
           FileDto FileDto;
           
           try {
    		   long id = willDto.getFileId();
    		   FileDto = fileService.getFile(id);
    	   }
    	   catch(Exception e) {
    		   FileDto= new FileDto((long) 0,"파일없음","파일없음","");
    	   }
           model.addAttribute("post", willDto);
           model.addAttribute("file", FileDto);
           return "will/edit.html";
       }
       //유언장 수정
       @PutMapping("/createwill/edit/{no}")
       public String update(@RequestParam("file") MultipartFile files, WillDto WillDto) {
    	   try {
        	   if(!files.getOriginalFilename().isEmpty())
        	   {
	                String origFilename = files.getOriginalFilename();
	                String filename = new MD5Generator(origFilename).toString();
	                /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
	                String savePath = System.getProperty("user.dir") + "\\files";
	                /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
	                if (!new File(savePath).exists()) {
	                    try{
	                        new File(savePath).mkdir();
	                    }
	                    catch(Exception e){
	                        e.getStackTrace();
	                    }
	                }
	                String filePath = savePath + "\\" + filename;
	                files.transferTo(new File(filePath));
	                FileDto fileDto = new FileDto();
	                fileDto.setOrigFilename(origFilename);
	                fileDto.setFilename(filename);
	                fileDto.setFilePath(filePath);
	                Long fileId = fileService.saveFile(fileDto);
	                WillDto.setFileId(fileId);
        	   }
        	    String Content = WillDto.getContent();
        	    String hashcontent = new MD5Generator(Content).toString();
        	    WillDto.setHashcontent(hashcontent);
                WillService.savePost(WillDto);   
           } catch(Exception e) {
                e.printStackTrace();
            }
           return "redirect:/user/will/list";
       }
       //유언장 삭제
       @DeleteMapping("/createwill/{no}")
       public String delete(@PathVariable("no") Long no) {
           willService.deleteWill(no);
           return "redirect:/user/will/list";
       }
       //파일 삭제
       @DeleteMapping("/edit/{id}")
       public String deletefile(@PathVariable("id") Long id) {
           fileService.deleteFile(id);
           return "/";
       }
       @GetMapping("/download/{fileId}")
       public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
           FileDto fileDto = fileService.getFile(fileId);
           Path path = Paths.get(fileDto.getFilePath());
           Resource resource = new InputStreamResource(Files.newInputStream(path));
           return ResponseEntity.ok()
                   .contentType(MediaType.parseMediaType("application/octet-stream"))
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileDto.getOrigFilename(),"utf-8") + "\"")
                   .body(resource);
       }
     //변호사 선택 페이지
       @GetMapping("/user/choice/{no}")
       public String choice(Model model,@PathVariable("no") Long no,@RequestParam(value = "page" , defaultValue="1")Integer pageNum) {
           List<MemberDto> LawyerDtoList = memberService.getMemberList(pageNum);
           Integer[] pageList = memberService.getPageList(pageNum);
           
            model.addAttribute("LawyerList", LawyerDtoList);
            model.addAttribute("pageList", pageList);
            model.addAttribute("WillId",no);
   	   
           return "will/choice";
       }
       
       //변호사 모달
       @GetMapping("/will/lawyer/{no}")
       public String modal(@PathVariable("no") Long no, Model model) {
           MemberDto lawyerDto = memberService.getPost(no);
           model.addAttribute("post", lawyerDto);
           
		return "will/lawyerdetail";
       }
           
           
       //변호사 지정
       @PostMapping("/will/choice/{no}")
        public String lawyerupdate(@PathVariable("no") Long no , Model model, WillDto willDto){

    	   Long WillNo = willDto.getNo();
    	   MemberEntity lawyerId = memberService.getMemberEntityByNo(no);
    	   
    	   willDto.setLawyerId(lawyerId.getId());
    	   willService.updatelawyerId(willDto);
    	return "redirect:/user/will/list";
       }
       
       
   }
