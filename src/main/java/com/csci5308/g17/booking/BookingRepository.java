package com.csci5308.g17.booking;

import com.csci5308.g17.config.DatabaseConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingRepository implements IBookingRepository {
    private static BookingRepository instance;

    private final JdbcTemplate db;

    public BookingRepository(JdbcTemplate db) {
        this.db = db;
    }

    public static BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository(DatabaseConfig.getJdbcTemplate());
        }
        return instance;
    }

}
