package com.csci5308.g17.timing;

import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class TimingDTO {

    private Integer id;
    private Integer facilityId;
    private String day;
    private String startTime;
    private String endTime;
    private Boolean isBlocking;

    public TimingDTO () {

    }

    public TimingDTO(Timing t) {
        id = t.getId();
        facilityId = t.getFacilityId();
        day = t.getDay().getDisplayName(TextStyle.FULL, Locale.CANADA);
        startTime = t.getStartTime().toString();
        endTime = t.getEndTime().toString();
        isBlocking = t.getIsBlocking();
    }

    public Integer getId() {
        return id;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Boolean getIsBlocking() {
        return isBlocking;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setIsBlocking(Boolean isBlocking) {
        this.isBlocking = isBlocking;
    }
}
