package com.will.dto;

import com.will.domain.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long no;
    private String name;
    private String id;
    private String password;
    private String email;
    private String birthdate;
    private String gender;
    private String hp;
    private String addr;
    private String proof;
    private String usertype;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String hasaddress;
    private boolean enabled = true;

    
    // has 어드레스 dto작성

    public MemberEntity toEntity() {
        MemberEntity memberEntity = MemberEntity.builder()
             .no(no)
             .name(name)
             .id(id)
             .password(password)
             .email(email)
             .birthdate(birthdate)
             .gender(gender)
             .hp(hp)
             .enabled(enabled)
             .addr(addr)
             .proof(proof)
             .usertype(usertype)
             .hasaddress(hasaddress)
             .build();
       return memberEntity;
    }

    @Builder
    public MemberDto(Long no, String name,  String id, String password, 
    		String email, String birthdate, String gender, String  hp, String addr,String proof, String usertype
    		,LocalDateTime createdDate, LocalDateTime modifiedDate, String hasadderss, Boolean enabled) {
        this.no = no;
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.gender =  gender;
        this.hp = hp;
        this.addr = addr;
        this.proof=proof;
        this.usertype=usertype;
        this.createdDate = createdDate;
        this.modifiedDate =modifiedDate;
        this.hasaddress = hasadderss;
        this.enabled = enabled;
    }










}