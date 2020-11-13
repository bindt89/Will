package com.will.config;

	

	import java.nio.charset.Charset;
	import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

	@Configuration
	@EnableWebMvc
	public class WebMvcConfig implements WebMvcConfigurer {

	    /**
	     * Resource 설정
	     * @param registry
	     */
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler(
	                "/images/**",
	                "/css/**",
	                "/js/**",
	                "/sass/**",
	        		"/webfonts/**"
	                )
	                .addResourceLocations(
	                        "classpath:/static/images/",
	                        "classpath:/static/css/",
	                        "classpath:/static/js/",
	                        "classpath:/static/sass/",
	                		"classpath:/static/webfonts/"
	                        );    
	        }

	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/").setViewName("forward:/app");
	        registry.addViewController("/app").setViewName("forward:/resource/index.html");
	        registry.addViewController("/app/**").setViewName("forward:/resource/index.html");
	    }

	    /**
	     * View Resolver 설정
	     * @return
	     */
	    @Bean
	    public InternalResourceViewResolver internalResourceViewResolver() {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/html/");
	        viewResolver.setSuffix(".html");
	        return viewResolver;
	    }

	    /**
	     * configureDefaultServletHandling
	     */
	    @Override
	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	        configurer.enable();
	    }

	    /**
	     * cors 설정 옵션추가
	     * @param registry
	     */
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("*")
	                .allowedMethods("*")
	                .allowedHeaders("*")
	                .allowCredentials(true);
	    }

	    @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true); // you USUALLY want this
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("PUT");
	        config.addAllowedMethod("DELETE");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }

	    @Bean
	    public StringHttpMessageConverter stringHttpMessageConverter() {
	        final StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
	        stringConverter.setSupportedMediaTypes(
	                Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
	        return stringConverter;
	    }


	}