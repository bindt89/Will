package com.will.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EqualsAndHashCode(callSuper=false, of = "email")
@Table(name = "member")
public class MemberEntity extends TimeEntity {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;

 
    
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String id;
    
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String birthdate;
   
    @Column(length = 100, nullable = false)
    private String hp;

    @Column(length = 100, nullable = false)
    private String gender;
    
    @Column(length = 100, nullable = false)
    private String addr;


    @Column
    private boolean enabled;
    
    @Builder
    public MemberEntity  (Long no, String name,  String id, String password, 
    		String email, String birthdate, String gender ,String  hp, String addr
    		,LocalDateTime createdDate, boolean enabled) {
    
        this.no = no;
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.hp = hp;
        this.addr = addr;
        this.enabled = enabled;
    }


	


}