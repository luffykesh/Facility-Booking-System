package com.csci5308.g17.booking;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingRowMapper implements RowMapper<Booking> {

    @Override
    public Booking mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Booking booking = new Booking();
        DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("y-M-d H:m:s");

        booking.setId(rs.getInt("id"));
        booking.setFacilityId(rs.getInt("facility_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setSlotId(rs.getInt("slot_id"));
        booking.setStartTime(LocalDateTime.parse(rs.getString("start_time"), dbFormat));
        booking.setEndTime(LocalDateTime.parse(rs.getString("end_time"), dbFormat));
        booking.setStatus(BookingStatus.valueOf(rs.getString("status")));

        return booking;
    }
}