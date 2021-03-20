package com.csci5308.g17.config;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class DatabaseConfig {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getJdbcTemplate() {
        if(jdbcTemplate == null) {
            ApplicationContext context = ApplicationContextProvider.getContext();
            DataSource ds = context.getBean(DataSource.class);
            jdbcTemplate = new JdbcTemplate(ds);
        }
        return jdbcTemplate;
    }
}
