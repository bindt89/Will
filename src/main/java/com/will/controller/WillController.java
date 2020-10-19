package com.will.controller;


   import com.will.dto.FileDto;
import com.will.dto.WillDto;
import com.will.service.WillService;
import com.will.util.MD5Generator;
import com.will.domain.entity.FileEntity;
import lombok.AllArgsConstructor;
import com.will.service.FileService;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
   

   @Controller
   @AllArgsConstructor
   public class WillController {
      
      private WillService WillService;
      private FileService FileService;

   
      //유언장 리스트   
       @GetMapping("/user/will/list")
       public String displist(Model model) {
           List<WillDto> WillDtoList = WillService.getWillList();
            model.addAttribute("WillList", WillDtoList);
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
                WillService.savePost(WillDto);
            } catch(Exception e) {
                e.printStackTrace();
            }
           return "redirect:/user/will/list";
       }
       
       @GetMapping("/createwill/{no}")
       public String detail(@PathVariable("no") Long no, Model model) {
           WillDto willDto = WillService.getPost(no);
           model.addAttribute("post", willDto);
           return "will/detail";
       }
       
       @GetMapping("/createwill/edit/{no}")
       public String edit(@PathVariable("no") Long no, Model model) {
           WillDto willDto = WillService.getPost(no);
           model.addAttribute("post", willDto);
           return "will/edit.html";
       }
       
       @PutMapping("/createwill/edit/{no}")
       public String update(WillDto willDto) {
           WillService.savePost(willDto);
           return "redirect:/user/will/list";
       }
       
       @DeleteMapping("/createwill/{no}")
       public String delete(@PathVariable("no") Long no) {
           WillService.deleteWill(no);
           return "redirect:/user/will/list";
       }
       @GetMapping("/download/{fileId}")
       public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
           FileDto fileDto = FileService.getFile(fileId);
           Path path = Paths.get(fileDto.getFilePath());
           Resource resource = new InputStreamResource(Files.newInputStream(path));
           return ResponseEntity.ok()
                   .contentType(MediaType.parseMediaType("application/octet-stream"))
                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getOrigFilename() + "\"")
                   .body(resource);
       }
       
   }
