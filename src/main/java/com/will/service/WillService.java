package com.will.service;

import com.will.domain.repository.MemberRepository;
import com.will.domain.repository.WillRepository;
import com.will.dto.WillDto;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.Sign.SignatureData;
import com.sun.mail.iap.ByteArray;
import com.will.domain.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.will.domain.entity.TimeEntity;

import javax.transaction.Transactional;
import com.will.domain.entity.WillEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WillService {
    private static WillRepository WillRepository;
    private static MemberRepository memberRepository;
    private static final int BLOCK_PAGE_NUM_COUNT= 5; // 블럭에 존재하는 페이지수
    private static final int PAGE_POST_COUNT = 4; //한페이지에 존재하는 게시글 수

    public WillService(WillRepository WillRepository, MemberRepository _memMemberRepository) {
        this.WillRepository = WillRepository;
        this.memberRepository = _memMemberRepository;
    }

    @Transactional
    public static Long savePost(WillDto willDto) {
        return WillRepository.save(willDto.toEntity()).getNo();
    }
    
  //변호사 지정 업데이트
    public void updatelawyerId(WillDto willDto){
    
       String lawyerId = willDto.getLawyerId();
        Long willNo = willDto.getNo();
        WillRepository.update(willNo ,lawyerId);
    }
    
    
    
    @Transactional
    public static List<WillDto> getWillList(Integer pageNum) {
    	
    	Page<WillEntity> page = WillRepository
    			.findAll(PageRequest
    					.of(pageNum-1,PAGE_POST_COUNT,Sort.by(Sort.Direction.ASC,"createdDate")));
    	
    	page.getContent();
        List<WillEntity> WillList = WillRepository.findAll();
        List<WillDto> WillDtoList = new ArrayList<>();

        for(WillEntity willEntity : WillList) {
        	
            WillDto WillDto = com.will.dto.WillDto.builder()
            		.no(willEntity.getNo())
            		.memberId(willEntity.getMemberId())
                    .title(willEntity.getTitle())
                    .content(willEntity.getContent())
                    .lawyerId(willEntity.getLawyerId())
                    .jinhang(willEntity.getJinhang())
                    .createdDate(willEntity.getCreatedDate())
                    
                   
                    .build();
            WillDtoList.add(WillDto);
        }
        return WillDtoList;
}
    @Transactional
    public WillDto getPost(Long no) {
    	
        WillEntity willEntity = WillRepository.findById(no).get();
        String lawyerId = (willEntity.getLawyerId()==null ? willEntity.getLawyerId() : "");
        WillDto WillDto = com.will.dto.WillDto.builder()
        		.no(willEntity.getNo())
                .memberId(willEntity.getMemberId())
                .title(willEntity.getTitle())
                .content(willEntity.getContent())
                .lawyerId(lawyerId)
                .jinhang(willEntity.getJinhang())
                .fileId(willEntity.getFileId())
                .createdDate(willEntity.getCreatedDate())
                
                .build();
        return WillDto;	
    }
    @Transactional
    public void deleteWill(Long no) {
        WillRepository.deleteById(no);
        
        
    }
    
    @Transactional
    public String encryptWill(Long willNo, Long userNo) {
       Optional<WillEntity> will = WillRepository.findById(willNo);
       
       String mnemonic = "among adult sock culture steel dream deer dutch pass length vehicle dial";
      Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(mnemonic, null));
      int[] derivationPath_user = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0,userNo.intValue()};
      Bip32ECKeyPair  userKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath_user);
      Credentials credentials = Credentials.create(userKeyPair);
      
      String msg = will.get().getContent();
      SignatureData signedMsg = Sign.signMessage(msg.getBytes(), credentials.getEcKeyPair());
      
      String result = signedMsg.toString();
       
       return result;
        
    }

    
    
    @Transactional
    public Integer[] getPageList(Integer curPageNum) {
    	Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
    	
    	//총게시글수
    	Double 	postsTotalCount = Double.valueOf(this.getwillCount());
    	
    	//총 게시글 수를 기준으로 계산한 마지막	페이지 번호 계산
    	Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
    	
    	//현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
    	Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
    			? curPageNum + BLOCK_PAGE_NUM_COUNT
    			: totalLastPageNum;
    	
    	//페이지 시작 번호 조정
    	curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;
    	
    	//페이지 번호 할당
    	for(int val=curPageNum, i=0;val<=blockLastPageNum;val++,i++) {
    		pageList[i] = val;
    	}
		return pageList;
    }

    @Transactional
    public Long getwillCount() {
    	return WillRepository.count();
    	
    }
}