package com.csci5308.g17.booking;

import java.util.Arrays;
import java.util.List;

import com.csci5308.g17.facility.Facility;
import com.csci5308.g17.facility.FacilityService;
import com.csci5308.g17.facility.IFacilityService;
import com.csci5308.g17.slot.ISlotService;
import com.csci5308.g17.slot.Slot;
import com.csci5308.g17.slot.SlotFullException;
import com.csci5308.g17.slot.SlotService;

import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService {

    private static BookingService instance;
    private IBookingRepository bookingRepository;
    private ISlotService slotService;
    private IFacilityService facilityService;

    public BookingService(IBookingRepository bookingRepository, ISlotService slotService,IFacilityService facilityService) {
        this.bookingRepository = bookingRepository;
        this.slotService = slotService;
        this.facilityService = facilityService;
    }

    public static BookingService getInstance() {
        if (instance == null) {
            instance = new BookingService(BookingRepository.getInstance(), SlotService.getInstance(), FacilityService.getInstance());
        }
        return instance;
    }

    public Booking createBooking(Integer userId, Integer slotId) throws SlotFullException{

        Slot slot = slotService.getSlotById(slotId);

        Booking booking = new Booking();
        booking.setFacilityId(slot.getFacilityId());
        booking.setUserId(userId);
        booking.setSlotId(slot.getId());
        booking.setStartTime(slot.getStartTime());
        booking.setEndTime(slot.getEndTime());

        Facility facility = facilityService.getFacilityById(slot.getFacilityId());
        if (facility.getApprovalRequired()) {
            booking.setStatus(BookingStatus.APPROVAL_PENDING);
        }
        else {
            booking.setStatus(BookingStatus.CONFIRMED);
        }

        booking = bookingRepository.addBooking(booking);
        slotService.reserveSeat(booking.getSlotId());
        return booking;
    }

    public void cancelBooking(Integer bookingId) {
        Booking booking = getById(bookingId);
        slotService.releaseSeat(booking.getSlotId());
        bookingRepository.setBookingStatus(bookingId, BookingStatus.CANCELLED);
    }

    public List<Booking> getUserBookings(Integer userId) {
        List<Booking> bookings = null;
        List<BookingStatus> requiredBookingStatus = Arrays.asList(BookingStatus.CONFIRMED, BookingStatus.CANCELLED, BookingStatus.APPROVAL_PENDING);
        bookings = bookingRepository.getUserBookings(userId, requiredBookingStatus);
        return bookings;
    }

    public Booking getById(Integer bookingId) {
        return bookingRepository.getById(bookingId);
    }

    public List<Booking> getFacilityBookings(Integer facilityId) {
        return bookingRepository.getFacilityBookings(facilityId);
    }

    public void approveBooking(Integer bookingId) {
        bookingRepository.setBookingStatus(bookingId, BookingStatus.CONFIRMED);
    }

    public void denyBooking(Integer bookingId) {
        Booking booking = getById(bookingId);
        slotService.releaseSeat(booking.getSlotId());
        bookingRepository.setBookingStatus(bookingId, BookingStatus.REJECTED);
    }

}
