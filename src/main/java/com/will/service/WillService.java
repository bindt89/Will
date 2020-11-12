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

    public WillService(WillRepository WillRepository, MemberRepository _memMemberRepository) {
        this.WillRepository = WillRepository;
        this.memberRepository = _memMemberRepository;
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
                    .memberId(willEntity.getMemberId())
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
                .memberId(willEntity.getMemberId())
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
    
 
    
    
}