package com.csci5308.g17.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@DependsOn("ApplicationContextProvider")
public class DatabaseConfig {

    public static JdbcTemplate getJdbcTemplate() {
        ApplicationContext context = ApplicationContextProvider.getContext();
        return context.getBean(JdbcTemplate.class);
    }
}
