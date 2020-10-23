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
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean enabled = true;


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
                .build();
       return memberEntity;
    }

    @Builder
    public MemberDto(Long no, String name,  String id, String password, 
    		String email, String birthdate, String gender, String  hp, String addr 
    		,LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean enabled) {
        this.no = no;
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.gender =  gender;
        this.hp = hp;
        this.addr = addr;
        this.createdDate = createdDate;
        this.modifiedDate =modifiedDate;
        this.enabled = enabled;
    }










}