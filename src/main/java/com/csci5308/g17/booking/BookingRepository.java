package com.csci5308.g17.booking;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import com.csci5308.g17.config.DatabaseConfig;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class BookingRepository implements IBookingRepository {
    private JdbcTemplate db;
    private static BookingRepository instance;

    private String Q_CREATE_BOOKING = "INSERT INTO booking(facility_id, user_id, slot_id, start_time, end_time, status) VALUES(?,?,?,?,?,?)";
    private String Q_GET_USER_BOOKING = "SELECT * FROM booking where user_id = :user_id AND status IN(:status_list) ORDER BY start_time DESC";
    private String Q_SET_BOOKING_STATUS = "UPDATE booking SET status = ? where id = ?";
    private String Q_GET_BY_ID = "SELECT * FROM booking WHERE id = ?";
    private String Q_FACILITY_BOOKINGS = "SELECT * FROM booking where facility_id = ? ORDER BY start_time DESC";

    public BookingRepository(JdbcTemplate db) {
        this.db = db;
    }

    public static BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository(DatabaseConfig.getJdbcTemplate());
        }
        return instance;
    }

    public Booking addBooking(Booking booking) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        db.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(Q_CREATE_BOOKING, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, booking.getFacilityId());
                ps.setInt(2, booking.getUserId());
                ps.setInt(3, booking.getSlotId());
                ps.setString(4, booking.getStartTime().toString());
                ps.setString(5, booking.getEndTime().toString());
                ps.setString(6, booking.getStatus().toString());
                return ps;
            },
            keyHolder
        );
        booking.setId(keyHolder.getKey().intValue());
        return booking;
    }

    public List<Booking> getUserBookings(Integer userId, List<BookingStatus> bookingStatues) {
        NamedParameterJdbcTemplate ndb = new NamedParameterJdbcTemplate(db);
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<String> statusList = bookingStatues.stream().map((status) -> status.toString()).collect(Collectors.toList());
        params.addValue("status_list", statusList);
        params.addValue("user_id", userId);
        return ndb.query(Q_GET_USER_BOOKING, params, new BookingRowMapper());
    }

    public void setBookingStatus(Integer bookingId, BookingStatus status) {
        db.update(Q_SET_BOOKING_STATUS, status.toString(), bookingId);
    }

    public Booking getById(Integer bookingId) {
        List <Booking> booking = db.query(Q_GET_BY_ID, new BookingRowMapper(), new Object[]{bookingId});
        if (booking == null || booking.size() == 0) {
            return null;
        }
        return booking.get(0);
    }

    public List<Booking> getFacilityBookings(Integer facilityId) {
        List <Booking> bookings = db.query(Q_FACILITY_BOOKINGS, new BookingRowMapper(), new Object[]{facilityId});
        return bookings;
    }
}
