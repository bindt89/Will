package com.will.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.will.domain.entity.MemberEntity;
import com.will.domain.entity.ReplyEntity;

	public interface ReplyRepositroy extends JpaRepository<ReplyEntity, Long> {
	}
	

