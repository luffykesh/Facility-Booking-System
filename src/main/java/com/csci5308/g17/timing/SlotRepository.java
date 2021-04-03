package com.csci5308.g17.timing;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlotRepository implements ISlotRepository{

    private JdbcTemplate db;
    private static SlotRepository instance;

    private final String Q_DELETE_SLOT = "DELETE FROM slot where id = ?";
    private final String Q_SLOTS_OF_FACILITY_ON_DATE = "SELECT * FROM slot WHERE facility_id = ? and start_time >= ? and end_time < ?";
    private final String Q_SLOTS_OF_TIMING = "SELECT * FROM slot where timing_id = ?";
    private final String Q_INSERT_SLOT = "INSERT INTO slot(facility_id, timing_id, total_seats, available_seats, start_time, end_time) VALUES(?,?,?,?,?,?)";
    private final String Q_DELETE_SLOTS_OF_TIMING = "DELETE FROM slot WHERE timing_id = ?";

    public SlotRepository(JdbcTemplate jdbcTemplate) {
        db = jdbcTemplate;
    }

    public static SlotRepository getInstance() {
        if(instance == null) {
            instance = new SlotRepository(DatabaseConfig.getJdbcTemplate());
        }
        return instance;
    }

    @Override
    public void deleteSlot(Integer id) {
        db.update(Q_DELETE_SLOT, id);
    }

    @Override
    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDate date) {
        List<Slot> slots = null;
        String start_time = date.toString();
        String end_time = date.plusDays(1).toString();
        slots = db.query(Q_SLOTS_OF_FACILITY_ON_DATE, new SlotRowMapper(), new Object[]{start_time, end_time});
        return slots;
    }

    @Override
    public List<Slot> getSlotsOfTiming(Integer timingId) {
        return db.query(Q_SLOTS_OF_TIMING, new SlotRowMapper(), new Object[]{timingId});
    }

    @Override
    public void insertSlots(List<Slot> slots) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement stmt, int index) throws SQLException {
                Slot slot = slots.get(index);
                stmt.setInt(1, slot.getFacilityId());
                stmt.setInt(2, slot.getTimingId());
                stmt.setInt(3, slot.getTotalSeats());
                stmt.setInt(4, slot.getAvailableSeats());
                stmt.setString(5, slot.getStartTime().toString());
                stmt.setString(6, slot.getEndTime().toString());
            }

            public int getBatchSize() {
                return slots.size();
            }
        };
        db.batchUpdate(Q_INSERT_SLOT, pss);
    }

    @Override
    public void deleteTimingSlots(Integer timingId) {
        db.update(Q_DELETE_SLOTS_OF_TIMING, timingId);
    }
}
