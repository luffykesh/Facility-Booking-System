package com.csci5308.g17.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.csci5308.g17.facility.Facility;
import com.csci5308.g17.facility.FacilityService;
import com.csci5308.g17.slot.Slot;
import com.csci5308.g17.slot.SlotFullException;
import com.csci5308.g17.slot.SlotService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BookingServiceTest {

    @Test
    public void createBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);
        Booking newBooking;

        final Integer USER_ID = 500;
        final Integer SLOT_ID = 100;
        final Integer FACILITY_ID = 10;
        Slot slot = new Slot();
        slot.setId(100);
        slot.setFacilityId(FACILITY_ID);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(LocalDateTime.now().plusHours(1));

        Mockito.when(slotService.getSlotById(SLOT_ID)).thenReturn(slot);
        try{
            Mockito.doNothing().when(slotService).reserveSeat(SLOT_ID);
        }
        catch(SlotFullException e){
            Assertions.fail();
        }

        Facility facility = new Facility();
        facility.setId(1);
        facility.setName("GYM");
        facility.setApprovalRequired(true);
        Mockito.when(facilityService.getFacilityById(slot.getFacilityId())).thenReturn(facility);
        Mockito.when(bookingRepo.addBooking(Mockito.any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);
        try {
            newBooking = bookingService.createBooking(USER_ID, SLOT_ID);

            Mockito.verify(slotService, Mockito.times(1)).reserveSeat(SLOT_ID);

            Assertions.assertEquals(SLOT_ID, newBooking.getSlotId());
            Assertions.assertEquals(USER_ID, newBooking.getUserId());
            Assertions.assertEquals(BookingStatus.APPROVAL_PENDING, newBooking.getStatus());
        }
        catch(SlotFullException e){
            Assertions.fail();
        }
    }

    @Test
    public void cancelBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);

        final Integer SLOT_ID = 100;
        final Integer BOOKING_ID = 500;

        Booking booking = new Booking();
        booking.setSlotId(SLOT_ID);
        booking.setId(BOOKING_ID);

        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Mockito.doNothing().when(slotService).releaseSeat(SLOT_ID);
        Mockito.doNothing().when(bookingRepo).setBookingStatus(BOOKING_ID, BookingStatus.CANCELLED);

        bookingService.cancelBooking(BOOKING_ID);

        Mockito.verify(slotService, Mockito.times(1)).releaseSeat(SLOT_ID);
    }

    @Test
    public void getUserBookingsTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);

        List<Booking> bookingList = new ArrayList<>();
        final Integer USER_ID = 10;
        final List<BookingStatus> status = new ArrayList<BookingStatus>();
        Mockito.when(bookingRepo.getUserBookings(USER_ID,status)).thenReturn(bookingList);
        List<Booking> returnedBooking = bookingService.getUserBookings(USER_ID);
        Assertions.assertNotNull(returnedBooking);
        Assertions.assertEquals(bookingList, returnedBooking);
    }

    @Test
    public void getFacilityBookingsTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);

        List<Booking> bookingList = new ArrayList<>();
        final Integer USER_ID = 10;
        final List<BookingStatus> status = new ArrayList<BookingStatus>();
        Mockito.when(bookingRepo.getFacilityBookings(USER_ID)).thenReturn(bookingList);
        List<Booking> returnedBooking = bookingService.getFacilityBookings(USER_ID);
        Assertions.assertNotNull(returnedBooking);
        Assertions.assertEquals(bookingList, returnedBooking);
    }

    @Test
    public void getByIdTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);

        Booking booking = new Booking();
        final Integer BOOKING_ID = 10;
        final List<BookingStatus> status = new ArrayList<BookingStatus>();
        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Booking returnedBooking = bookingService.getById(BOOKING_ID);
        Assertions.assertNotNull(returnedBooking);
        Assertions.assertEquals(booking, returnedBooking);
    }

    @Test
    public void approveBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);

        final Integer SLOT_ID = 100;
        final Integer BOOKING_ID = 500;

        Booking booking = new Booking();
        booking.setSlotId(SLOT_ID);
        booking.setId(BOOKING_ID);

        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Mockito.doNothing().when(bookingRepo).setBookingStatus(BOOKING_ID, BookingStatus.CONFIRMED);

        bookingService.approveBooking(BOOKING_ID);
        Assertions.assertNotNull(booking);

    }

    @Test
    public void denyBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService);

        final Integer SLOT_ID = 100;
        final Integer BOOKING_ID = 500;

        Booking booking = new Booking();
        booking.setSlotId(SLOT_ID);
        booking.setId(BOOKING_ID);

        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Mockito.doNothing().when(slotService).releaseSeat(SLOT_ID);
        Mockito.doNothing().when(bookingRepo).setBookingStatus(BOOKING_ID, BookingStatus.REJECTED);

        bookingService.denyBooking(BOOKING_ID);

        Mockito.verify(slotService, Mockito.times(1)).releaseSeat(SLOT_ID);
    }


}
