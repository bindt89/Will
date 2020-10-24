package com.will.domain.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.will.domain.entity.CertifiedEntity;



public interface CertifiedRepository extends JpaRepository<CertifiedEntity, Long> {

	CertifiedEntity findCertifiedEntityByEmail(String email);
	
	CertifiedEntity findCertifiedEntityByNumber(String number);
	
}