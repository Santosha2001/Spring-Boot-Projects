package com.xworkz.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Slf4j
public class SpringWebInit extends AbstractAnnotationConfigDispatcherServletInitializer implements WebMvcConfigurer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        log.info("this is getServletConfigClasses");
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        log.info("this is getServletConfigClasses");
        return new Class[]{BeanConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        log.info("this is getServletMappings");
        return new String[]{"/"};
    }

    //    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
