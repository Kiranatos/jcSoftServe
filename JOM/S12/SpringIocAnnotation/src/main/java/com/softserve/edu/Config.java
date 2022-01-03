package com.softserve.edu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.softserve.edu.service.ConvertServise;
import com.softserve.edu.service.impl.ConvertServiseImpl;

@Configuration
@ComponentScan("com.softserve.edu.service")
public class Config {
    
    @Bean//(name = "convertServise")
    public ConvertServise createConvertServise() {
        return ConvertServiseImpl.getConvertServise();
    }
}