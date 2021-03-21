package com.csci5308.g17.facility;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacilityRowMapper implements RowMapper<Facility> {
    @Override
    public Facility mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Facility facility = new Facility();
        facility.setId(rs.getInt("id"));
        facility.setName(rs.getString("name"));
        facility.setDescription(rs.getString("description"));
        facility.setLocation(rs.getString("location"));
        facility.setOccupancy(rs.getInt("occupancy"));
        facility.setManager_id(rs.getInt("manager_id"));
        facility.setTime_slot(rs.getString("time_slot"));
        facility.setActive(rs.getBoolean("active"));
        facility.setApproval_required(rs.getBoolean("approval_required"));
        return facility;
    }
}
