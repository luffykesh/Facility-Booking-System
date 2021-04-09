package com.csci5308.g17.booking;

import java.util.List;

import com.csci5308.g17.slot.SlotFullException;

public interface IBookingService {
    public Booking createBooking(Integer userId, Integer slot) throws SlotFullException;

    public void cancelBooking(Integer bookingId);

    public List<Booking> getUserBookings(Integer userId);

    public Booking getById(Integer bookingId);

    public List<Booking> getFacilityBookings(Integer facilityId);

    public void approveBooking(Integer bookingId);

    public void denyBooking(Integer bookingId);
}
