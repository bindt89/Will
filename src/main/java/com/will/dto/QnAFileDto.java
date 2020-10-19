package com.will.dto;

import lombok.*;
import com.will.domain.entity.FileEntity;
import com.will.domain.entity.QnAFileEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QnAFileDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public QnAFileEntity toEntity() {
    	QnAFileEntity build = QnAFileEntity.builder()
                .id(id)
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();
        return build;
    }

    @Builder
    public QnAFileDto(Long id, String origFilename, String filename, String filePath) {
        this.id = id;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}