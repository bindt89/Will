package com.will.domain.repository;




import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.will.domain.entity.LawyerEntity;
import com.will.domain.entity.MemberEntity;



public interface LawyerRepository extends JpaRepository<LawyerEntity, Long> {
//  Optional<MemberEntity> findById(String id);

	

	
	  LawyerEntity findById(String id);
	    
	    LawyerEntity findLawyerEntityById(Long no);
		
	    LawyerEntity findLawyerEntityByEmail(Long no);
	    
	    LawyerEntity findLawyerEntityByEmail(String email);
		
	    LawyerEntity findLawyerEntityById(String id);
	@Transactional
	@Modifying	// update , delete Query시 @Modifying 어노테이션을 추가
	@Query(value="UPDATE LawyerEntity me SET me.password = :password WHERE me.no = :no", nativeQuery=false)
	void update(@Param("no") Long no, @Param("password") String password);
}