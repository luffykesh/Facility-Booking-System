package com.csci5308.g17.timing;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Timing {
    private Integer id;
    private Integer facilityId;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isBlocking;

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

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsBlocking() {
        return isBlocking;
    }

    public void setIsBlocking(Boolean isBlocking) {
        this.isBlocking = isBlocking;
    }

    @Override
    public String toString() {
        return String.format(
            "Timing(id=%d, facilityId=%d, day=%s, startTime=%s, endTime=%s, isBlocking=%s)",
            id, facilityId, day, startTime, endTime, isBlocking
        );
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(other == null || getClass() != other.getClass()) {
            return false;
        }

        Timing t2 = (Timing)other;

        if(this == t2) {
            return true;
        }

        return (
            this.day.equals(t2.getDay())
            && this.startTime.equals(t2.getStartTime())
            && this.endTime.equals(t2.getEndTime())
            && this.isBlocking.equals(t2.getIsBlocking())
            && this.facilityId.equals(t2.getFacilityId())
        );
    }
}
