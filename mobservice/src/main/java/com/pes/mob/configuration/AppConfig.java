package com.pes.mob.configuration;
 
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.pes.mob.controller.FourSquareController;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.pes.mob")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AppConfig {
     
    @Bean
    public ViewResolver viewResolver() {
    	// temporarly - but must be deleted
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
//    @Bean
//    public FourSquareController search(){
//		return new FourSquareController();
//		}
//    @Bean
//    public MyAspect myAspect() {
//        return new MyAspect();
//    }
}