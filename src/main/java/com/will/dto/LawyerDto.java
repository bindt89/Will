package com.will.dto;


import lombok.*;

import java.time.LocalDateTime;

import com.will.domain.entity.LawyerEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LawyerDto {
    private Long no;
    private String name;
    private String id;
    private String password;
    private String email;
    private String birthdate;
    private String proof;
    private String gender;
    private String hp;
    private String addr;
    private LocalDateTime createdDate2;
    private LocalDateTime modifiedDate2;


    public  LawyerEntity toEntity() {
    	LawyerEntity lawyerEntity = LawyerEntity.builder()
             .no(no)
             .name(name)
             .id(id)
             .password(password)
             .email(email)
             .birthdate(birthdate)
             .proof(proof)
             .gender(gender)
             .hp(hp)
             .addr(addr)
             .build();
       return lawyerEntity;
    }

    @Builder
    public LawyerDto(Long no, String name,  String id, String password, 
    		String email, String birthdate, String proof, String gender, String  hp, String addr) {
        this.no = no;
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.proof = proof;
        this.gender =  gender;
        this.hp = hp;
        this.addr = addr;
    }

}