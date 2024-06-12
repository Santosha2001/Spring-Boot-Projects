package com.scm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class BeanConfiguration {

    /*
     * @Value is to get values from the properties file.
     */
    @Value("${clodinary.cloud.name}")
    private String cloudName;

    @Value("${clodinary.api.key}")
    private String apiKey;

    @Value("${clodinary.api.secret}")
    private String apiSecret;

    public BeanConfiguration() {
        System.out.println("BeanConfiguration");
    }

    // cloudinary vconfiguration for image upload
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret));
    }
}
