package com.hoiio.controller.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hoiio.util.DateUtil;

@Configuration
@EnableScheduling
public class ControllerConfig {
    @Bean
    ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(module);
        objectMapper.setDateFormat(DateUtil.format());

        return objectMapper;
    }
}
