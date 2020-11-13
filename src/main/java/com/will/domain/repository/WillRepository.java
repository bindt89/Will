package com.will.domain.repository;

import com.will.domain.entity.MemberEntity;
import com.will.domain.entity.WillEntity;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WillRepository extends JpaRepository<WillEntity, Long> {
	@Transactional
	@Modifying	// update , delete Query시 @Modifying 어노테이션을 추가
	@Query(value="UPDATE WillEntity me SET me.lawyerId = :lawyerId WHERE me.no = :no", nativeQuery=false)
	void update(@Param("no") Long no, @Param("lawyerId") String lawyerId);
	
}