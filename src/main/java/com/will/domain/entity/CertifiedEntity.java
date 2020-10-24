package com.will.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EqualsAndHashCode(callSuper=false, of = "email")
@Table(name = "certifiednumber")
public class CertifiedEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
 
 	@Column(length = 50, nullable = false)
    private String email;
 
 	@Column(length = 10, nullable = false)
    private String number;

 	@Builder
 	CertifiedEntity(Long no, String email, String number){
	 this.no = no;
	 this.email = email;
	 this.number = number;
 	}

}