package com.will.controller;


   import com.will.dto.FileDto;
import com.will.dto.QnAFileDto;
import com.will.dto.WillDto;
import com.will.service.WillService;
import com.will.util.MD5Generator;
import com.will.domain.MemberDetail;
import com.will.domain.entity.FileEntity;
import com.will.domain.entity.WillEntity;

import lombok.AllArgsConstructor;
import com.will.service.FileService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.ArrayList;
import java.util.List;
   

   @Controller
   @AllArgsConstructor
   public class WillController {
      
      private WillService WillService;
      private FileService FileService;

   
      //유언장 리스트   
       @GetMapping("/user/will/list")
       public String displist(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
           List<WillDto> WillDtoList = WillService.getWillList();
           List<WillDto> result = new ArrayList<WillDto>();
           
           for(int i=0;i<WillDtoList.size(); i++) {
        	   if(WillDtoList.get(i).getMemberId().equals(memberDetail.getUsername())) {
        		   result.add(WillDtoList.get(i));
        	   }
        	   else if(WillDtoList.get(i).getMemberId1() != null && WillDtoList.get(i).getLawyerId() !=null )
        	   {
        			   result.add(WillDtoList.get(i));
			   } 
    	   }
           model.addAttribute("WillList", result);
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
                   Long fileId = FileService.saveFile(fileDto);
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
           WillDto willDto = WillService.getPost(no);
           FileDto FileDto;
           
           
    	   try {
    		   long id = willDto.getFileId();
    		   FileDto = FileService.getFile(id);
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
           WillDto willDto = WillService.getPost(no);
           FileDto FileDto;
           
           try {
    		   long id = willDto.getFileId();
    		   FileDto = FileService.getFile(id);
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
                   Long fileId = FileService.saveFile(fileDto);
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
           WillService.deleteWill(no);
           return "redirect:/user/will/list";
       }
       //파일 삭제
       @DeleteMapping("/edit/{id}")
       public String deletefile(@PathVariable("id") Long id) {
           FileService.deleteFile(id);
          
           
           return "/";
       }
       @GetMapping("/download/{fileId}")
       public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
           FileDto fileDto = FileService.getFile(fileId);
           Path path = Paths.get(fileDto.getFilePath());
           Resource resource = new InputStreamResource(Files.newInputStream(path));
           return ResponseEntity.ok()
                   .contentType(MediaType.parseMediaType("application/octet-stream"))
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileDto.getOrigFilename(),"utf-8") + "\"")
                   .body(resource);

       }
       
     //변호사 선택 페이지
       @GetMapping("/user/choice")
       public String dispchoice() {
           return "will/choice";
       }
       
     //전자서명 하기
       @PostMapping("/will/sign")
       public String willsign(Long willNo , Long userNo)  {
    	   return WillService.encryptWill(willNo, userNo);
       }
       
       
   }
