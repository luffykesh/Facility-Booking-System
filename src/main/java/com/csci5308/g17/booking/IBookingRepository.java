package com.csci5308.g17.booking;

import java.util.List;

public interface IBookingRepository {
    public Booking addBooking(Booking booking);

    public List<Booking> getUserBookings(Integer userId, List<BookingStatus> bookingStatues);

    public void setBookingStatus(Integer bookingId, BookingStatus status);

    public Booking getById(Integer bookingId);

    public List<Booking> getFacilityBookings(Integer facilityId);
}
