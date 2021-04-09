package com.csci5308.g17.timing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;

public class TimingRowMapper implements RowMapper<Timing> {

    @Override
    public Timing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timing timing = new Timing();
        timing.setId(rs.getInt("id"));
        timing.setFacilityId(rs.getInt("facility_id"));
        timing.setIsBlocking(rs.getBoolean("is_blocking"));

        DayOfWeek dow = DayOfWeek.of(rs.getInt("day"));
        LocalTime startTime = LocalTime.parse(rs.getString("start_time"));
        LocalTime endTime = LocalTime.parse(rs.getString("end_time"));

        timing.setDay(dow);
        timing.setStartTime(startTime);
        timing.setEndTime(endTime);

        return timing;
    }
}
