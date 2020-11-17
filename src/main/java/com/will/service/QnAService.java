package com.will.service;

import com.will.domain.repository.QnARepository;
import com.will.domain.repository.ReplyRepositroy;
import com.will.dto.QnADto;
import com.will.dto.ReplyDto;

import org.springframework.stereotype.Service;
import com.will.domain.entity.TimeEntity;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import com.will.domain.entity.QnAEntity;
import com.will.domain.entity.ReplyEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class QnAService {
    private static QnARepository qnARepository;
    private static ReplyRepositroy replyRepository;

    public QnAService(QnARepository QnARepository,ReplyRepositroy replyRepositroy) {
        this.qnARepository = QnARepository;
        this.replyRepository = replyRepositroy;
    }
    //qna save
    @Transactional
    public static Long savePost(QnADto QnADto) {
        return qnARepository.save(QnADto.toEntity()).getNo();
    }
    //댓글 save
    @Transactional
    public static Long saveReply(ReplyDto replyDto) {
        return replyRepository.save(replyDto.toEntity()).getNo();
    }
    //QnA 리스튼
    @Transactional
    public List<QnADto> getQnAList() {
        List<QnAEntity> QnAList = qnARepository.findAll();
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
    //Reply 리스트
    @Transactional
    public List<ReplyDto> getReplyList() {
        List<ReplyEntity> replyList = replyRepository.findAll();
        List<ReplyDto> replyDtoList = new ArrayList<>();
        for(ReplyEntity replyEntity : replyList) {
        	ReplyDto replyDto = com.will.dto.ReplyDto.builder()
            		.no(replyEntity.getNo())
                    .memberId(replyEntity.getMemberId())
                    .title(replyEntity.getTitle())
                    .content(replyEntity.getContent())
                    .createdDate(replyEntity.getCreatedDate())
                    .qnano(replyEntity.getQnano())
                    .build();
        	replyDtoList.add(replyDto);
        }
        return replyDtoList;
}
    //QnA no로 가져오기
    @Transactional
    public QnADto getPost(Long no) {
    	QnAEntity QnAEntity = qnARepository.findById(no).get();

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
    //Reply no로 가져오기
    @Transactional
    public ReplyDto getReply(Long no) {
    	ReplyEntity replyEntity = replyRepository.findById(no).get();

    	ReplyDto replyDto = com.will.dto.ReplyDto.builder()
        		.no(replyEntity.getNo())
                .title(replyEntity.getTitle())
                .memberId(replyEntity.getMemberId())
                .content(replyEntity.getContent())
                .createdDate(replyEntity.getCreatedDate())
                .build();
        return replyDto;
    }
    @Transactional
    public void deleteQnA(Long no) {
    	qnARepository.deleteById(no);
    }
}