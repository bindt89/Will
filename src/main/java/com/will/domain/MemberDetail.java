//package com.will.domain;
//
//
//
//	import com.will.domain.entity.MemberEntity;
//	import com.will.domain.entity.Role;
//	import org.springframework.security.core.GrantedAuthority;
//	import org.springframework.security.core.authority.SimpleGrantedAuthority;
//	import org.springframework.security.core.userdetails.UserDetails;
//
//	import java.util.*;
//
//	public class MemberDetail implements UserDetails {
//		
//	    private MemberEntity memberEntity;
//
//	    public MemberDetail(MemberEntity memberEntity){
//	        this.memberEntity = memberEntity;
//	    }
//
//	    @Override
//	    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//	        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//
//	        if(("admin").equals(memberEntity.getId())) {
//	            authorityList.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
//	        } else {
//	            authorityList.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
//	        }
//	        return authorityList;
//	    }
//
//	    @Override
//	    public String getPassword() {
//	        return memberEntity.getPassword();
//	    }
//
//
//	    @Override
//	    public String getUserHp() {
//	        return memberEntity.getHp();
//	    }
//	    
//	    @Override
//	    public String getUserd() {
//	        return memberEntity.getId();
//	    }
//	   
//	    @Override
//	    public String getUseraddr() {
//	        return memberEntity.getAddr();
//	    }
//	    
//	    @Override
//	    public boolean isAccountNonExpired() {
//	        return true;
//	    }
//
//	    @Override
//	    public boolean isAccountNonLocked() {
//	        return true;
//	    }
//
//	    @Override
//	    public boolean isCredentialsNonExpired() {
//	        return true;
//	    }
//
//	    @Override
//	    public boolean isEnabled() {
//	        return memberEntity.isEnabled();
//	    }
//
//	}
//	
//	
//	
//	
//
