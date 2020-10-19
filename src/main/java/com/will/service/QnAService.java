package com.will.service;

import com.will.domain.repository.QnARepository;
import com.will.dto.QnADto;
import org.springframework.stereotype.Service;
import com.will.domain.entity.TimeEntity;

import javax.transaction.Transactional;
import com.will.domain.entity.QnAEntity;
import java.util.ArrayList;
import java.util.List;

@Service
public class QnAService {
    private static QnARepository QnARepository;

    public QnAService(QnARepository QnARepository) {
        this.QnARepository = QnARepository;
    }

    @Transactional
    public static Long savePost(QnADto QnADto) {
        return QnARepository.save(QnADto.toEntity()).getNo();
    }
    
    @Transactional
    public List<QnADto> getQnAList() {
        List<QnAEntity> QnAList = QnARepository.findAll();
        List<QnADto> QnADtoList = new ArrayList<>();

        for(QnAEntity QnAEntity : QnAList) {
        	QnADto QnADto = com.will.dto.QnADto.builder()
            		.no(QnAEntity.getNo())
                    .id(QnAEntity.getId())
                    .title(QnAEntity.getTitle())
                    .content(QnAEntity.getContent())
                    .createdDate(QnAEntity.getCreatedDate())
                    
                   
                    .build();
        	QnADtoList.add(QnADto);
        }
        return QnADtoList;
}
    @Transactional
    public QnADto getPost(Long no) {
    	QnAEntity QnAEntity = QnARepository.findById(no).get();

    	QnADto QnADto = com.will.dto.QnADto.builder()
        		.no(QnAEntity.getNo())
                .id(QnAEntity.getId())
                .title(QnAEntity.getTitle())
                .content(QnAEntity.getContent())
                .fileId(QnAEntity.getFileId())
                .createdDate(QnAEntity.getCreatedDate())
                .build();
        return QnADto;
    }
    @Transactional
    public void deleteQnA(Long no) {
    	QnARepository.deleteById(no);
    }

}