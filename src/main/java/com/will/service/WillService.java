package com.will.service;

import com.will.domain.repository.WillRepository;
import com.will.dto.WillDto;
import org.springframework.stereotype.Service;
import com.will.domain.entity.TimeEntity;

import javax.transaction.Transactional;
import com.will.domain.entity.WillEntity;
import java.util.ArrayList;
import java.util.List;

@Service
public class WillService {
    private static WillRepository WillRepository;

    public WillService(WillRepository WillRepository) {
        this.WillRepository = WillRepository;
    }

    @Transactional
    public static Long savePost(WillDto WillDto) {
        return WillRepository.save(WillDto.toEntity()).getNo();
    }
    
    @Transactional
    public List<WillDto> getWillList() {
        List<WillEntity> WillList = WillRepository.findAll();
        List<WillDto> WillDtoList = new ArrayList<>();

        for(WillEntity willEntity : WillList) {
            WillDto WillDto = com.will.dto.WillDto.builder()
            		.no(willEntity.getNo())
                    .id(willEntity.getId())
                    .title(willEntity.getTitle())
                    .content(willEntity.getContent())
                    .createdDate(willEntity.getCreatedDate())
                    
                   
                    .build();
            WillDtoList.add(WillDto);
        }
        return WillDtoList;
}
    @Transactional
    public WillDto getPost(Long no) {
        WillEntity willEntity = WillRepository.findById(no).get();

        WillDto WillDto = com.will.dto.WillDto.builder()
        		.no(willEntity.getNo())
                .id(willEntity.getId())
                .title(willEntity.getTitle())
                .content(willEntity.getContent())
                .fileId(willEntity.getFileId())
                .createdDate(willEntity.getCreatedDate())
                .build();
        return WillDto;
    }
    @Transactional
    public void deleteWill(Long no) {
        WillRepository.deleteById(no);
    }

}