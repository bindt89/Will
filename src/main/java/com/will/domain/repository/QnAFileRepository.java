package com.will.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.will.domain.entity.QnAFileEntity;

public interface QnAFileRepository extends JpaRepository<QnAFileEntity, Long> {
}