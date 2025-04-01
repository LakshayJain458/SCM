package com.scm.SCM.Configurations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageConfig {

    @Value("${cloudinary.cloud.name}")
    private String cloudname;
    @Value("${cloudinary.api.key}")
    private String apikey;
    @Value("${cloudinary.api.secret}")
    private String apisecret;

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudname,
                        "api_key", apikey,
                        "api_secret", apisecret
                )
        );
    }

}
