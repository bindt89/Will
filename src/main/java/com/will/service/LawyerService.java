package com.will.service;


import com.will.domain.LawyerDetail;
import com.will.domain.entity.LawyerEntity;
import com.will.domain.repository.LawyerRepository;
import com.will.dto.LawyerDto;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LawyerService implements UserDetailsService {
   
	private LawyerRepository lawyerRepository;

    @Transactional
    public Long joinUser(LawyerDto lawyerDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        lawyerDto.setPassword(passwordEncoder.encode(lawyerDto.getPassword()));

        return lawyerRepository.save(lawyerDto.toEntity()).getNo();
    }
    
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

    	LawyerEntity lawyerEntity = lawyerRepository.findLawyerEntityById(id);
        if(lawyerEntity == null) {
            throw  new UsernameNotFoundException("Could not find user with that email");
        }
        
        return new LawyerDetail(lawyerEntity);
    	
    		
    }

    
    @Transactional
    public Long savePost(LawyerDto lawyerDto) {
        return lawyerRepository.save(lawyerDto.toEntity()).getNo();
    }

    @Transactional
    public List<LawyerDto> getLawyerlist() {
        List<LawyerEntity> lawyerEntities = lawyerRepository.findAll();
        List<LawyerDto> lawyerDtoList = new ArrayList<>();

        for (LawyerEntity lawyerEntity : lawyerEntities) {
        	LawyerDto lawyerDto = LawyerDto.builder()
                    .no(lawyerEntity.getNo())
                    .id(lawyerEntity.getId())
                    .email(lawyerEntity.getEmail())
                    .name(lawyerEntity.getName())
                    .hp(lawyerEntity.getHp())
                    .birthdate(lawyerEntity.getBirthdate())
                    .createdDate2(lawyerEntity.getCreatedDate())
                    .build();

            
           lawyerDtoList.add(lawyerDto);
        
        }
        return lawyerDtoList;
    }
    
    @Transactional
    public LawyerDto getLawyer(Long no) {
        Optional<LawyerEntity> lawyerEntityWraper = lawyerRepository.findById(no);
        LawyerEntity lawyerEntity = lawyerEntityWraper.get();

        LawyerDto lawyerDto = LawyerDto.builder()
        		.no(lawyerEntity.getNo())
                .id(lawyerEntity.getId())
                .email(lawyerEntity.getEmail())
                .name(lawyerEntity.getName())
                .hp(lawyerEntity.getHp())
                .birthdate(lawyerEntity.getBirthdate())
                .createdDate2(lawyerEntity.getCreatedDate())
                .build();

        return lawyerDto;
    }

    //입력한 email과 name이 일치하는 값을 찾는 요청
   	public boolean userEmailCheck(String id, String name) {

           LawyerEntity lawyerEntity = lawyerRepository.findLawyerEntityByEmail(id);
           if(lawyerEntity!=null && lawyerEntity.getName().equals(name)) {
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
        Long no = lawyerRepository.findLawyerEntityById(id).getNo();
        lawyerRepository.update(no, password);
    }
    
    //회원가입 email 중복확인
    public String idCheck(String email) {
        System.out.println(lawyerRepository.findLawyerEntityByEmail(email));

        if (lawyerRepository.findLawyerEntityByEmail(email) == null) {
            return "YES";
        } else {
            return "NO";
        }
    }
    
//    //email로 발송 된 인증번호와 입력한 인증번호가 일치하는지 확인
//    public String CertifiedCheck(String number) {
//    	CertifiedEntity certifiedEntity = certifiedRepository.findCertifiedEntityByNumber(number);
//	        if(certifiedEntity!=null && certifiedEntity.getNumber().equals(number)) {
//	            return "YES";
//	        }
//	        else {
//	            return "NO";
//	        }
//    }
    
}