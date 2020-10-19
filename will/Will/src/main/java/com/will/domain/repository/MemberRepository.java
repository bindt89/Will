package com.will.domain.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.will.domain.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findById(String id);
    
}