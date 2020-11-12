package com.will.domain.repository;

import com.will.domain.entity.MemberEntity;
import com.will.domain.entity.WillEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WillRepository extends JpaRepository<WillEntity, Long> {
	
	Optional<MemberEntity> findByMemberId(String memid);
}