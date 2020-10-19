package com.will.service;


import com.will.domain.entity.QnAFileEntity;
import com.will.domain.repository.QnAFileRepository;
import com.will.dto.FileDto;
import com.will.dto.QnAFileDto;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class QnAFileService {
    private QnAFileRepository QnAfileRepository;

    public QnAFileService(QnAFileRepository QnAfileRepository) {
        this.QnAfileRepository = QnAfileRepository;
    }

    @Transactional
    public Long saveQnAFile(QnAFileDto QnAfileDto) {
        return QnAfileRepository.save(QnAfileDto.toEntity()).getId();
    }

    @Transactional
    public QnAFileDto getFile(Long id) {
    	QnAFileEntity file = QnAfileRepository.findById(id).get();

    	QnAFileDto QnAfileDto = QnAFileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return QnAfileDto;
    }
}