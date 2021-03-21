package com.csci5308.g17.config;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseConfig {

    public static JdbcTemplate getJdbcTemplate() {
        ApplicationContext context = ApplicationContextProvider.getContext();
        return context.getBean(JdbcTemplate.class);
    }
}
