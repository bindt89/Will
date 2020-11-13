package com.will.domain.repository;




import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.will.domain.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//   Optional<MemberEntity> findById(String id);

	

	
	  MemberEntity findById(String id);
	  
	  MemberEntity findByNo(Long no);
	    
	    MemberEntity findMemberEntityById(Long no);
		
	    MemberEntity findMemberEntityByEmail(Long no);
	    
		MemberEntity findMemberEntityByEmail(String email);
		
		MemberEntity findMemberEntityById(String id);
	@Transactional
	@Modifying	// update , delete Query시 @Modifying 어노테이션을 추가
	@Query(value="UPDATE MemberEntity me SET me.password = :password WHERE me.no = :no", nativeQuery=false)
	void update(@Param("no") Long no, @Param("password") String password);
	
    @Transactional
    @Modifying    
    @Query(value="UPDATE MemberEntity me SET me.hasaddress = :hasaddress WHERE me.no = :no", nativeQuery=false)
    void updateAddress(@Param("no") Long no, @Param("hasaddress") String hasaddress);
    
   
	
	
    
}