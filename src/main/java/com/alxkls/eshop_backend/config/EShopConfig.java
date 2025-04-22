package com.alxkls.eshop_backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EShopConfig {


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
