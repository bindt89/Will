package com.will.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
//@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
@Table(name = "Will") 
public class WillEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long no;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    @Column(length = 100, nullable = false)
    private String hashcontent;
    
    @Column(length = 100, nullable = false)
    private String jinhang;
    
    @Column
    private String memberId;
    
    @Column
    private String lawyerId;

    @Column
    private Long fileId;
    
    @Column
    private String member1;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

  

    @Builder
    public WillEntity(Long no, String memberId , String member1, String title, String content, String hashcontent , String lawyerId , String jinhang , Long fileId) {
    	this.no = no;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.hashcontent = hashcontent;
        this.lawyerId = lawyerId;
        this.jinhang = jinhang;
        this.fileId = fileId;
        this.member1 = member1;
    }
}