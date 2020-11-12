package com.will.dto;
import com.will.domain.entity.WillEntity;
import com.will.domain.repository.MemberRepository;

import lombok.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WillDto {
    private Long no;
    private String title;
    private String content;
    private String hashcontent;
    private String jinhang;
    private String memberId;
    private String lawyerId;
    private String memberId1;
    private Long fileId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public WillEntity toEntity() {
       
       WillEntity willentity = WillEntity.builder()
             .no(no)
                .memberId(memberId)
                .title(title)
                .content(content)
                .hashcontent(hashcontent)
                .lawyerId(lawyerId)
                .jinhang(jinhang)
                .fileId(fileId)
                .memberId(memberId1)
                .build();
        return willentity;
    }

    @Builder
    public WillDto(Long no, String memberId , String memberId1, String title, String content,String hashcontent, String lawyerId, String jinhang,Long fileId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
    	this.no = no;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.hashcontent = hashcontent;
        this.lawyerId = lawyerId;
        this.jinhang = jinhang;
        this.fileId = fileId;
        this.memberId1 = memberId1;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
      
    }
}