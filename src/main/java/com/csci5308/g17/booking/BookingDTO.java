package com.csci5308.g17.booking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class BookingDTO {

    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("y-M-d H:m:s");
    private Integer id;
    private Integer facilitId;
    private Integer userId;
    private Integer slotId;
    private String startTime;
    private String endTime;
    private String status;

    public BookingDTO() {
    }

    public BookingDTO(Integer id, Integer facilitId, Integer userId, Integer slotId, String startTime,
        String endTime, String status) {
        this.id = id;
        this.facilitId = facilitId;
        this.userId = userId;
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public BookingDTO(Booking booking) {
        id = booking.getId();
        facilitId = booking.getFacilityId();
        userId = booking.getUserId();
        slotId = booking.getSlotId();
        startTime = booking.getStartTime().format(dtFormatter);
        endTime = booking.getEndTime().format(dtFormatter);
        status = booking.getStatus().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFacilitId() {
        return facilitId;
    }

    public void setFacilitId(Integer facilitId) {
        this.facilitId = facilitId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Booking toBooking() {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setFacilityId(facilitId);
        booking.setUserId(userId);
        booking.setSlotId(slotId);
        booking.setStatus(BookingStatus.valueOf(status.toUpperCase()));
        booking.setStartTime(LocalDateTime.parse(startTime));
        booking.setEndTime(LocalDateTime.parse(endTime));
        return booking;
    }
}
