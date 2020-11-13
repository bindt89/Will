package com.will.config;

import com.will.service.MemberService;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	private MemberService memberService;
	
	

    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
 }
 
    
 
   
  

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/libs/**", "/sass/**",
        		"/webfonts/**");
    }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/", "/oauth2/**","/image/**","/login/**", "/login2/**")
		.permitAll()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/myinfo2").hasRole("MEMBER")
			
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                
             // law 페이지 권한 설정
                .antMatchers("/myinfo4").hasRole("LAWYER")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and() 
                
                .csrf()
    			.ignoringAntMatchers("/check/findPw/sendEmail")
    			.ignoringAntMatchers("/check/Pw")
    			.ignoringAntMatchers("/check/Pw/changePw")
    			.ignoringAntMatchers("/idCheck/sendEmail")
    			.ignoringAntMatchers("/CertifiedCheck")
                
                
                
    			.and()// 로그인 설정
               .formLogin()
               .loginPage("/user/login")
                .defaultSuccessUrl("/user/login/result")
                .loginProcessingUrl("/user/login/result")
               .permitAll()
                .and() 
        
				
//				  // 변호사 로그인 설정 
//                .formLogin() 
//                .loginPage("/user/login1")
//				  .defaultSuccessUrl("/user/login/result1")
//				 .loginProcessingUrl("/user/login/result1") 
//				 .permitAll()
//				 .and()
				 
                
                // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and() 
              
            
                // 403 예외처리 핸들링
               .exceptionHandling().accessDeniedPage("/user/denied");
        		//http.csrf().disable();
        
    }
   

               


	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}
 
}