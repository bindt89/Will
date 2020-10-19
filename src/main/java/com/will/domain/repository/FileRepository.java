package com.will.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.will.domain.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}