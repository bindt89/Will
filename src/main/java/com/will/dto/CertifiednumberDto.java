package com.will.dto;

import com.will.domain.entity.CertifiedEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertifiednumberDto {

	private Long no;
	private String email;
	private String number;
	
	public CertifiedEntity toEntity() {
		
		CertifiedEntity certifiedEntity = CertifiedEntity.builder()
                .no(no)
                .email(email)
                .number(number)
                .build();
        return certifiedEntity;		
	}
	
	@Builder
    public CertifiednumberDto(Long no, String email, String number) {
    	this.no = no;
        this.email = email;
        this.number = number;
    }
	
}
