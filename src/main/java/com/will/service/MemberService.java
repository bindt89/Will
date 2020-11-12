package com.will.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

import com.will.domain.MemberDetail;
import com.will.domain.Role;
import com.will.domain.entity.CertifiedEntity;
import com.will.domain.entity.MemberEntity;
import com.will.domain.repository.CertifiedRepository;
import com.will.domain.repository.MemberRepository;
import com.will.dto.MemberDto;

import lombok.AllArgsConstructor;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;


import org.web3j.crypto.Bip32ECKeyPair;
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
   
	  @Autowired
		private MemberRepository memberRepository;
	  private   CertifiedRepository certifiedRepository;

	
	public String getUserAddress(Long userNum) {
		String mnemonic = "among adult sock culture steel dream deer dutch pass length vehicle dial";
		Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(mnemonic, null));
		int[] derivationPath_user = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0,userNum.intValue()};
		Bip32ECKeyPair  userKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath_user);
		Credentials credentials = Credentials.create(userKeyPair);
		
		return credentials.getAddress();
	}

	
	  
	
	  @Transactional public Long joinUser(MemberDto memberDto) { // 비밀번호 암호화
	  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
	  memberDto.setHasaddress("");
	  
	  Long userNumber = memberRepository.save(memberDto.toEntity()).getNo(); 
	  String userAddress = getUserAddress(userNumber);
	  memberRepository.updateAddress(userNumber, userAddress);
	  
	  return userNumber;
	  
	  
	  }
	 


	@Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

    	MemberEntity memberEntity = memberRepository.findMemberEntityById(id);
        if(memberEntity == null) {
            throw  new UsernameNotFoundException("Could not find user with that email");
        }
        
        return new MemberDetail(memberEntity);
    	
    	
    	
    	
//        Optional<MemberEntity> userEntityWrapper = memberRepository.findById(id);
//        MemberEntity userEntity = userEntityWrapper.get();
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if(("admin").equals(id)) {
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
//        }
//
//        return new User(userEntity.getId(), userEntity.getPassword(), authorities);
    }

    
    @Transactional
    public Long savePost(MemberDto memberDto) {
        return memberRepository.save(memberDto.toEntity()).getNo();
    }

    @Transactional
    public List<MemberDto> getMemberlist() {
        List<MemberEntity> memberEntities = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (MemberEntity memberEntity : memberEntities) {
            MemberDto memberDto = MemberDto.builder()
                    .no(memberEntity.getNo())
                    .id(memberEntity.getId())
                    .email(memberEntity.getEmail())
                    .name(memberEntity.getName())
                    .hp(memberEntity.getHp())
                    .birthdate(memberEntity.getBirthdate())
                    .createdDate(memberEntity.getCreatedDate())
                    .build();

            
            memberDtoList.add(memberDto);
        
        }
        return memberDtoList;
    }
    
    @Transactional
    public MemberDto getMember(Long no) {
        Optional<MemberEntity> memberEntityWraper = memberRepository.findById(no);
        MemberEntity memberEntity = memberEntityWraper.get();

        MemberDto memberDto = MemberDto.builder()
        		.no(memberEntity.getNo())
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .name(memberEntity.getName())
                .hp(memberEntity.getHp())
                .birthdate(memberEntity.getBirthdate())
                .createdDate(memberEntity.getCreatedDate())
                .build();

        return memberDto;
    }

    //입력한 email과 name이 일치하는 값을 찾는 요청
   	public boolean userEmailCheck(String id, String name) {

           MemberEntity memberEntity = memberRepository.findMemberEntityByEmail(id);
           if(memberEntity!=null && memberEntity.getName().equals(name)) {
               return true;
           }
           else {
               return false;
           }
       }

    //해당 유저의 패스워드 변경
    public void updatePassword(String newpw, String id){
    	

    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String password = passwordEncoder.encode(newpw);
        Long no = memberRepository.findMemberEntityById(id).getNo();
        memberRepository.update(no, password);
    }
    
    //회원가입 email 중복확인
    public String idCheck(String email) {
        System.out.println(memberRepository.findMemberEntityByEmail(email));

        if (memberRepository.findMemberEntityByEmail(email) == null) {
            return "YES";
        } else {
            return "NO";
        }
    }
    
    //email로 발송 된 인증번호와 입력한 인증번호가 일치하는지 확인
    public String CertifiedCheck(String number) {
    	CertifiedEntity certifiedEntity = certifiedRepository.findCertifiedEntityByNumber(number);
	        if(certifiedEntity!=null && certifiedEntity.getNumber().equals(number)) {
	            return "YES";
	        }
	        else {
	            return "NO";
	        }
    }
    
}