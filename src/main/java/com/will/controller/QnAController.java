package com.will.controller;

import com.will.dto.QnAFileDto;
import com.will.dto.ReplyDto;
import com.will.domain.entity.MemberEntity;
import com.will.dto.QnADto;
import com.will.service.QnAService;
import com.will.util.QnAMD5Generator;
import lombok.AllArgsConstructor;
import com.will.service.QnAFileService;

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

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
   

   @Controller
   @AllArgsConstructor
   public class QnAController {
      
      private QnAService QnAService;
      private QnAFileService QnAFileService;

   
      //QnA 리스트   
       @GetMapping("/user/QnA/list")
       public String dispQnAlist(Model model) {
           List<QnADto> QnADtoList = QnAService.getQnAList();
           List<ReplyDto> replyDtoList = QnAService.getReplyList();
            model.addAttribute("QnAList", QnADtoList);
            model.addAttribute("ReplyList", replyDtoList);
           return "QnA/list";
       }
       //QnA 작성 페이지
       @GetMapping("/user/write")
       public String dispQnAwrite() {
           return "QnA/write";
       }
       //QnA db입력
       @PostMapping("/user/write")
       public String QnAwrite(@RequestParam("file") MultipartFile files, QnADto QnADto) {
           try {
        	   if(!files.getOriginalFilename().isEmpty())
        	   {
                String origFilename = files.getOriginalFilename();
                String filename = new QnAMD5Generator(origFilename).toString();
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

                QnAFileDto QnAfileDto = new QnAFileDto();
                QnAfileDto.setOrigFilename(origFilename);
                QnAfileDto.setFilename(filename);
                QnAfileDto.setFilePath(filePath);

                Long fileId = QnAFileService.saveQnAFile(QnAfileDto);
                QnADto.setFileId(fileId);
                
            } 
           
                 QnAService.savePost(QnADto);
                 
           }catch(Exception e) {
                     e.printStackTrace();
                 }
           return "redirect:/user/QnA/list";
       }
       //QnA 디테일
       @GetMapping("/write/{no}")
       public String QnAdetail(@PathVariable("no") Long no ,Model model) {
    	   QnADto qnADto = QnAService.getPost(no);
    	   QnAFileDto qnAFileDto;
    	   try {
    		   Long id = qnADto.getFileId();
    		   qnAFileDto = QnAFileService.getFile(id);
    	   }
    	   catch(Exception e) {
    		   
    		   qnAFileDto= new QnAFileDto((long) 0,"파일없음","파일없음","");
    	   }
           model.addAttribute("post", qnADto);
           model.addAttribute("file",qnAFileDto);
           return "QnA/detail";
       }
       //QnA수정 페이지
       @GetMapping("/write/edit/{no}")
       public String QnAedit(@PathVariable("no") Long no, Model model) {
    	   QnADto QnADto = QnAService.getPost(no);
           model.addAttribute("post", QnADto);
           return "QnA/edit.html";
       }
       
       @PutMapping("/write/edit/{no}")
       public String QnAupdate(QnADto QnADto) {
    	   QnAService.savePost(QnADto);
           return "redirect:/user/QnA/list";
       }
       
       @DeleteMapping("/write/{no}")
       public String QnAdelete(@PathVariable("no") Long no) {
    	   QnAService.deleteQnA(no);
           return "redirect:/user/QnA/list";
       }
       @GetMapping("/QnAdownload/{fileId}")
       public ResponseEntity<Resource> QnAfileDownload(@PathVariable("fileId") Long fileId) throws IOException {
    	   QnAFileDto QnAfileDto = QnAFileService.getFile(fileId);
           Path path = Paths.get(QnAfileDto.getFilePath());
           Resource resource = new InputStreamResource(Files.newInputStream(path));
           return ResponseEntity.ok()
                   .contentType(MediaType.parseMediaType("application/octet-stream"))
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(QnAfileDto.getOrigFilename(),"utf-8") + "\"")
                   .body(resource);
       }
     //댓글 작성 페이지
       @GetMapping("/QnA/reply/{no}")
       public String dispReply(@PathVariable("no") Long no) {
           return "QnA/reply";
       }
     //Reply db입력
       @PostMapping("/QnA/reply")
       public String Reply(ReplyDto replyDto, QnADto qnaDto) {
    	   Long qnaNo = qnaDto.getNo();
    	   replyDto.setQnano(qnaNo);
    	   QnAService.saveReply(replyDto);
		return "redirect:/user/QnA/list";   
       }
       //Reply 디테일
       @GetMapping("/reply/{no}")
       public String Replydetail(@PathVariable("no") Long no ,Model model) {
    	   ReplyDto replyDto = QnAService.getReply(no);
           model.addAttribute("reply", replyDto);
           return "QnA/replydetail";
       }       
       //Reply수정 페이지
       @GetMapping("/reply/edit/{no}")
       public String Replyedit(@PathVariable("no") Long no, Model model) {
    	   ReplyDto replyDto = QnAService.getReply(no);
           model.addAttribute("reply", replyDto);
           return "QnA/replyedit.html";
       }       
   }
