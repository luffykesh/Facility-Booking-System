package com.csci5308.g17.timing;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


@Component
public class TimingRepository implements ITimingRepository {

    private JdbcTemplate db;
    private static TimingRepository instance;

    private String Q_DELETE_BY_ID = "DELETE FROM timing WHERE id=?";
    private String Q_INSERT_TIMING = "INSERT INTO timing(facility_id, day, start_time, end_time, is_blocking) VALUES (?,?,?,?,?)";
    private String Q_GET_BY_ID = "SELECT * FROM timing WHERE id=?";
    private String Q_GET_BY_FACILITY_ID = "SELECT * FROM timing WHERE facility_id=?";

    public TimingRepository(JdbcTemplate jdbcTemplate) {
        db = jdbcTemplate;
    }

    public static TimingRepository getInstance() {
        if(instance == null) {
            instance = new TimingRepository(DatabaseConfig.getJdbcTemplate());
        }
        return instance;
    }

    @Override
    public Timing insertTiming(Timing timing) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String start_time = timing.getStartTime().toString();
        String end_time = timing.getEndTime().toString();

        db.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(Q_INSERT_TIMING, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, timing.getFacilityId());
                ps.setInt(2, timing.getDay().getValue());
                ps.setString(3, start_time);
                ps.setString(4, end_time);
                ps.setBoolean(5, timing.getIsBlocking());
                return ps;
                },
            keyHolder
        );
        timing.setId(keyHolder.getKey().intValue());
        return timing;
    }

    @Override
    public void deleteTimingById(Integer id) {
        db.update(Q_DELETE_BY_ID, id);
    }

    @Override
    public Timing getTimingById(Integer id) {
        List<Timing> timings = db.query(Q_GET_BY_ID, new TimingRowMapper(), new Object[] {id});
        if(timings.size()==0) {
            return null;
        }
        return timings.get(0);
    }

    @Override
    public List<Timing> getTimingsbyFacilityId(Integer facilityId) {
        List<Timing> timings = db.query(Q_GET_BY_FACILITY_ID, new TimingRowMapper(), new Object[] {facilityId});
        return timings;
    }
}
