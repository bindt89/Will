package com.will.domain.repository;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.will.domain.entity.LawyerEntity;



public interface LawyerRepository extends JpaRepository<LawyerEntity, Long> {
    Optional<LawyerEntity> findByEmail(String email);
    
}