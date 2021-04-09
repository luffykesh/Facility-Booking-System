package com.csci5308.g17.slot;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlotRepository implements ISlotRepository{

    private JdbcTemplate db;
    private static SlotRepository instance;

    private final String Q_DELETE_SLOT = "DELETE FROM slot WHERE id = ?";
    private final String Q_SLOTS_OF_FACILITY_ON_DATE = "SELECT * FROM slot WHERE facility_id = ? and start_time >= ? and end_time < ? ORDER BY start_time";
    private final String Q_SLOTS_OF_TIMING = "SELECT * FROM slot WHERE timing_id = ? ORDER BY start_time";
    private final String Q_INSERT_SLOT = "INSERT INTO slot(facility_id, timing_id, total_seats, available_seats, start_time, end_time) VALUES(?,?,?,?,?,?)";
    private final String Q_DELETE_SLOTS_OF_TIMING = "DELETE FROM slot WHERE timing_id = ?";
    private final String Q_RESERVE_SEAT = "UPDATE slot SET available_seats=available_seats-1 WHERE id=? and available_seats>0";
    private final String Q_RELEASE_SEAT = "UPDATE slot SET available_seats=available_seats+1 WHERE id=? and available_seats<total_seats";
    private final String Q_SLOT_BY_ID = "SELECT * FROM slot WHERE id = ?";

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
    public List<Slot> getSlotsForFacility(Integer facilityId, LocalDateTime fromInclusive, LocalDateTime toExclusive) {
        List<Slot> slots = null;
        slots = db.query(Q_SLOTS_OF_FACILITY_ON_DATE, new SlotRowMapper(), new Object[]{facilityId, fromInclusive.toString(), toExclusive.toString()});
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

    @Override
    public void releaseSeat(Integer slotId) {
        db.update(Q_RELEASE_SEAT, slotId);
    }

    @Override
    public void reserveSeat(Integer slotId) {
        db.update(Q_RESERVE_SEAT, slotId);
    }

    @Override
    public Slot getSlotById(Integer slotId) {
        List<Slot> slot = db.query(Q_SLOT_BY_ID, new SlotRowMapper(), new Object[] {slotId});
        if(slot == null || slot.size() == 0) {
            return null;
        }
        return slot.get(0);
    }
}
