package com.will.service;


import com.will.domain.Role2;
import com.will.domain.entity.LawyerEntity;
import com.will.domain.repository.LawyerRepository;
import com.will.dto.LawyerDto;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<LawyerEntity> userEntityWrapper = lawyerRepository.findByEmail(email);
        LawyerEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin2").equals(email)) {
            authorities.add(new SimpleGrantedAuthority(Role2.ADMIN2.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role2.LAWYER.getValue()));
        }

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
    
 
    
    
}