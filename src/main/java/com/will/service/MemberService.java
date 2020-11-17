package com.will.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.will.domain.MemberDetail;
import com.will.domain.Role;
import com.will.domain.entity.CertifiedEntity;
import com.will.domain.entity.MemberEntity;
import com.will.domain.entity.WillEntity;
import com.will.domain.repository.CertifiedRepository;
import com.will.domain.repository.MemberRepository;
import com.will.dto.MemberDto;
import com.will.dto.WillDto;
import lombok.AllArgsConstructor;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Bip32ECKeyPair;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
	 private static final int BLOCK_PAGE_NUM_COUNT= 5; // 블럭에 존재하는 페이지수
	    private static final int PAGE_POST_COUNT = 4; //한페이지에 존재하는 게시글 수
	
   
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
                    .proof(memberEntity.getProof())
                    .usertype(memberEntity.getUsertype())
                    .birthdate(memberEntity.getBirthdate())
                    .createdDate(memberEntity.getCreatedDate())
                    .enabled(true)
                    .build();

            
            memberDtoList.add(memberDto);
        
        }
        return memberDtoList;
    }
    
    @Transactional
    public List<MemberDto> getMemberList(Integer pageNum) {
    	
    	Page<MemberEntity> page = memberRepository
    			.findAll(PageRequest
    					.of(pageNum-1,PAGE_POST_COUNT,Sort.by(Sort.Direction.ASC,"createdDate")));
    	
    	page.getContent();
        List<MemberEntity> MemberList = memberRepository.findAll();
        List<MemberDto> MemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : MemberList) {
        	if(memberEntity.getUsertype().equals("member")) continue;
            MemberDto MemberDto = com.will.dto.MemberDto.builder()
            		.no(memberEntity.getNo())
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .addr(memberEntity.getAddr())
                    .birthdate(memberEntity.getBirthdate())
                    .hp(memberEntity.getHp())
                    .proof(memberEntity.getProof())
                    .usertype(memberEntity.getUsertype())
                    .createdDate(memberEntity.getCreatedDate())
                    .email(memberEntity.getEmail())
                    .enabled(memberEntity.isEnabled())
                    
                    .build();
            MemberDtoList.add(MemberDto);
        }
        
        	
        return MemberDtoList;
}
    
    @Transactional
    public Integer[] getPageList(Integer curPageNum) {
    	Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
    	
    	//총게시글수
    	Double 	postsTotalCount = Double.valueOf(this.getmemberCount());
    	
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
    public Long getmemberCount() {
    	return memberRepository.count();
    	
    }

    
    @Transactional
    public MemberDto getMember(String id) {
        MemberEntity memberEntity = memberRepository.findById(id);

        MemberDto memberDto = MemberDto.builder()
        		.password(memberEntity.getPassword())
        		.gender(memberEntity.getGender())
        		.no(memberEntity.getNo())
                .id(memberEntity.getId())
                .email(memberEntity.getEmail())
                .addr(memberEntity.getAddr())
                .hasadderss(memberEntity.getHasaddress())
                .name(memberEntity.getName())
                .hp(memberEntity.getHp())
                .usertype(memberEntity.getUsertype())
                .proof(memberEntity.getProof())
                .birthdate(memberEntity.getBirthdate())
                .createdDate(memberEntity.getCreatedDate())
                .enabled(true)
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
        memberRepository.Changepass(no, password);
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
    
    @Transactional
    public MemberDto getPost(Long no) {
    	
        MemberEntity memberEntity = memberRepository.findByNo(no);

        MemberDto memberDto = com.will.dto.MemberDto.builder()
        		.no(memberEntity.getNo())
                .addr(memberEntity.getAddr())
                .birthdate(memberEntity.getBirthdate())
                .email(memberEntity.getEmail())
                .gender(memberEntity.getGender())
                .hp(memberEntity.getHp())
                .name(memberEntity.getName())
                
                
                .build();
        return memberDto;	
    }
    
    @Transactional
    public MemberEntity getMemberEntityByNo(Long no) {
    	
        MemberEntity memberEntity = memberRepository.findByNo(no);

        
        return memberEntity;	
    }
    
}