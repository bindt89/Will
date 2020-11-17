package com.will.dto;

import com.will.domain.entity.ReplyEntity;
import com.will.domain.entity.WillEntity;
import com.will.domain.repository.MemberRepository;

import lombok.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {
    private Long no;
    private Long qnano;
    private String title;
    private String content;
    private String memberId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ReplyEntity toEntity() {
    	
    	ReplyEntity replyentity = ReplyEntity.builder()
    			.no(no)
    			.qnano(qnano)
    			.title(title)
                .memberId(memberId)
                .content(content)
                .build();
        return replyentity;
    }

    @Builder
    public ReplyDto(Long no, Long qnano, String memberId , String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
    	this.no = no;
    	this.qnano = qnano;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
      
    }
}