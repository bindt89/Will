package com.will.dto;

import com.will.domain.entity.WillEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WillDto {
    private Long no;
    private String id;
    private String title;
    private String content;
    private Long fileId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public WillEntity toEntity() {
    	WillEntity willentity = WillEntity.builder()
    			.no(no)
                .id(id)
                .title(title)
                .content(content)
                .fileId(fileId)
                .build();
        return willentity;
    }

    @Builder
    public WillDto(Long no, String id , String title, String content,Long fileId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
    	this.no = no;
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
      
    }
}