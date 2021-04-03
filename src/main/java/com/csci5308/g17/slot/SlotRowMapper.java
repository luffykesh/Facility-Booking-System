package com.csci5308.g17.slot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

public class SlotRowMapper implements RowMapper<Slot> {

    @Override
    public Slot mapRow(ResultSet rs, int rowNum) throws SQLException {
        Slot slot = new Slot();
        DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("y-M-d H:m:s");

        slot.setId(rs.getInt("id"));
        slot.setFacilityId(rs.getInt("facility_id"));
        slot.setTimingId(rs.getInt("timing_id"));
        slot.setTotalSeats(rs.getInt("total_seats"));
        slot.setAvailableSeats(rs.getInt("available_seats"));
        slot.setStartTime(LocalDateTime.parse(rs.getString("start_time"), dbFormat));
        slot.setEndTime(LocalDateTime.parse(rs.getString("end_time"), dbFormat));

        return slot;
    }
}
