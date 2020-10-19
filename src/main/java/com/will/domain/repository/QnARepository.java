package com.will.domain.repository;

import com.will.domain.entity.MemberEntity;
import com.will.domain.entity.QnAEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnAEntity, Long> {
	
	Optional<MemberEntity> findById(String id);
}