package com.will.dto;

import com.will.domain.entity.QnAEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QnADto {
    private Long no;
    private String id;
    private String title;
    private String content;
    private Long fileId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public QnAEntity toEntity() {
    	QnAEntity QnAentity = QnAEntity.builder()
    			.no(no)
                .id(id)
                .title(title)
                .content(content)
                .fileId(fileId)
                .build();
        return QnAentity;
    }

    @Builder
    public QnADto(Long no, String id , String title, String content,Long fileId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
    	this.no = no;
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
      
    }
}