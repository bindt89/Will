package com.will;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.web.filter.HiddenHttpMethodFilter;


@EnableJpaAuditing
@SpringBootApplication
public class WillApplication {

	public static void main(String[] args) {
		SpringApplication.run(WillApplication.class, args);
	}

  @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
   }

  
  
  
}