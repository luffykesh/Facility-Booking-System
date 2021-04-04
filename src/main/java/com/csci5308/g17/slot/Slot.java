package com.csci5308.g17.slot;

import java.time.LocalDateTime;

public class Slot {

    private Integer id;
    private Integer facilityId;
    private Integer timingId;
    private Integer totalSeats;
    private Integer availableSeats;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Integer getTimingId() {
        return timingId;
    }

    public void setTimingId(Integer timingId) {
        this.timingId = timingId;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return String.format(
            "Slot(id=%d, facilityId=%d, timingId=%d, totalSeats=%d, availableSeats=%d, startTime=%s, endTime=%s)",
            id, facilityId, timingId, totalSeats ,availableSeats, startTime, endTime
        );
    }
}
