package com.will.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.will.domain.Role;
import com.will.domain.entity.MemberEntity;
import com.will.domain.repository.MemberRepository;
import com.will.dto.MemberDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
   
	private MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getNo();
    }
    
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<MemberEntity> userEntityWrapper = memberRepository.findById(id);
        MemberEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin").equals(id)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getId(), userEntity.getPassword(), authorities);
    }


    @Transactional
    public List<MemberDto> getMemberlist() {
        List<MemberEntity> memberEntities = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();

        for (MemberEntity memberEntity : memberEntities) {
            MemberDto memberDto = MemberDto.builder()
                    .no(memberEntity.getNo())
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
                .email(memberEntity.getEmail())
                .name(memberEntity.getName())
                .hp(memberEntity.getHp())
                .birthdate(memberEntity.getBirthdate())
                .createdDate(memberEntity.getCreatedDate())
                .build();

        return memberDto;
    }

//    //입력한 email과 name이 일치하는 값을 찾는 요청
//   	public boolean userEmailCheck(String id, String name) {
//
//           MemberEntity memberEntity = memberRepository.findMemberEntityByEmail(id);
//           if(memberEntity!=null && memberEntity.getName().equals(name)) {
//               return true;
//           }
//           else {
//               return false;
//           }
//       }
//
//       //해당 유저의 패스워드 변경
//       public void updatePassword(String newpw, String email){
//       	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//       	String password = passwordEncoder.encode(newpw);
//           String id = memberRepository.findMemberEntityByEmail(email).getId();
//           memberRepository.update(no, password);
//       } 
//       
//       public String idCheck(String email) {
//           System.out.println(memberRepository.findMemberEntityByEmail(email));
//
//           if (memberRepository.findMemberEntityByEmail(email) == null) {
//               return "YES";
//           } else {
//               return "NO";
//           }
//
//       }
    
}