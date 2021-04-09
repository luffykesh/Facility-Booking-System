package com.csci5308.g17.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.csci5308.g17.email.EmailService;
import com.csci5308.g17.facility.Facility;
import com.csci5308.g17.facility.FacilityService;
import com.csci5308.g17.slot.Slot;
import com.csci5308.g17.slot.SlotFullException;
import com.csci5308.g17.slot.SlotService;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BookingServiceTest {

    @Test
    public void createBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);
        Booking newBooking;

        final Integer USER_ID = 500;
        final Integer SLOT_ID = 100;
        final Integer FACILITY_ID = 10;
        Slot slot = new Slot();
        slot.setId(100);
        slot.setFacilityId(FACILITY_ID);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(LocalDateTime.now().plusHours(1));
        User user = new User();
        user.setId(USER_ID);
        user.setEmail("janedoe@dal.ca");

        Mockito.when(slotService.getSlotById(SLOT_ID)).thenReturn(slot);
        Mockito.when(userService.getUserById(USER_ID)).thenReturn(user);
        try{
            Mockito.doNothing().when(slotService).reserveSeat(SLOT_ID);
        }
        catch(SlotFullException e){
            Assertions.fail();
        }

        Facility facility = new Facility();
        facility.setId(FACILITY_ID);
        facility.setName("GYM");
        facility.setApprovalRequired(true);
        Mockito.when(facilityService.getFacilityById(slot.getFacilityId())).thenReturn(facility);
        Mockito.when(bookingRepo.addBooking(Mockito.any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

        // test when facility.approvalRequired = true
        try {
            newBooking = bookingService.createBooking(USER_ID, SLOT_ID);

            Assertions.assertEquals(SLOT_ID, newBooking.getSlotId());
            Assertions.assertEquals(USER_ID, newBooking.getUserId());
            Assertions.assertEquals(BookingStatus.APPROVAL_PENDING, newBooking.getStatus());
            Mockito.verify(slotService, Mockito.times(1)).reserveSeat(SLOT_ID);
            Mockito.verifyNoInteractions(emailService);
        }
        catch(SlotFullException e){
            Assertions.fail();
        }

        // test when facility.approvalRequired = false
        facility.setApprovalRequired(false);
        Mockito.clearInvocations(slotService);
        Mockito.clearInvocations(emailService);
        Mockito.clearInvocations(userService);
        try {
            newBooking = bookingService.createBooking(USER_ID, SLOT_ID);

            Assertions.assertEquals(SLOT_ID, newBooking.getSlotId());
            Assertions.assertEquals(USER_ID, newBooking.getUserId());
            Assertions.assertEquals(BookingStatus.CONFIRMED, newBooking.getStatus());
            Mockito.verify(slotService, Mockito.times(1)).reserveSeat(SLOT_ID);
            Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString());;
        }
        catch(SlotFullException e) {
            Assertions.fail();
        }
        catch(MessagingException e) {
            Assertions.fail();
        }
    }

    @Test
    public void cancelBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);

        final Integer SLOT_ID = 100;
        final Integer BOOKING_ID = 500;
        final Integer USER_ID = 1000;
        final Integer FACILITY_ID = 1234;

        Facility facility = new Facility();
        facility.setId(FACILITY_ID);
        facility.setName("GYM");
        facility.setApprovalRequired(true);

        User user = new User();
        user.setId(USER_ID);
        user.setEmail("janedoe@dal.ca");

        Booking booking = new Booking();
        booking.setSlotId(SLOT_ID);
        booking.setId(BOOKING_ID);
        booking.setUserId(USER_ID);
        booking.setFacilityId(FACILITY_ID);
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusDays(1));

        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Mockito.doNothing().when(slotService).releaseSeat(SLOT_ID);
        Mockito.doNothing().when(bookingRepo).setBookingStatus(BOOKING_ID, BookingStatus.CANCELLED);
        Mockito.when(userService.getUserById(USER_ID)).thenReturn(user);
        Mockito.when(facilityService.getFacilityById(FACILITY_ID)).thenReturn(facility);

        bookingService.cancelBooking(BOOKING_ID);

        Mockito.verify(slotService, Mockito.times(1)).releaseSeat(SLOT_ID);
        try{
            Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString());
        } catch(MessagingException e) {
            Assertions.fail();
        }
    }

    @Test
    public void getUserBookingsTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);

        List<Booking> bookingList = new ArrayList<>();
        final Integer USER_ID = 10;
        final List<BookingStatus> status = new ArrayList<BookingStatus>();

        Mockito.when(bookingRepo.getUserBookings(USER_ID,status)).thenReturn(bookingList);
        List<Booking> returnedBooking = bookingService.getUserBookings(USER_ID);
        Assertions.assertNotNull(returnedBooking);
        Assertions.assertEquals(bookingList, returnedBooking);
        Mockito.verifyNoInteractions(emailService);
    }

    @Test
    public void getFacilityBookingsTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);
        List<Booking> bookingList = new ArrayList<>();
        final Integer USER_ID = 10;
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
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);
        Booking booking = new Booking();
        final Integer BOOKING_ID = 10;
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
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);

        final Integer SLOT_ID = 100;
        final Integer BOOKING_ID = 500;
        final Integer USER_ID = 1000;
        final Integer FACILITY_ID = 1234;

        User user = new User();
        user.setId(USER_ID);
        user.setEmail("janedoe@dal.ca");

        Facility facility = new Facility();
        facility.setId(FACILITY_ID);
        facility.setName("GYM");
        facility.setApprovalRequired(true);

        Booking booking = new Booking();
        booking.setSlotId(SLOT_ID);
        booking.setUserId(USER_ID);
        booking.setId(BOOKING_ID);
        booking.setFacilityId(FACILITY_ID);
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusDays(1));

        Mockito.when(userService.getUserById(USER_ID)).thenReturn(user);
        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Mockito.doNothing().when(bookingRepo).setBookingStatus(BOOKING_ID, BookingStatus.CONFIRMED);
        Mockito.when(facilityService.getFacilityById(FACILITY_ID)).thenReturn(facility);
        try {
            Mockito.doNothing().when(emailService).sendEmail(Mockito.anyString(), Mockito.anyString());
        }
        catch (MessagingException e) {
            Assertions.fail();
        }

        bookingService.approveBooking(BOOKING_ID);
        Assertions.assertNotNull(booking);
        try {
            Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString());;
        }
        catch (MessagingException e) {
            Assertions.fail();
        }
        Mockito.verify(userService, Mockito.times(1)).getUserById(USER_ID);
    }

    @Test
    public void denyBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        FacilityService facilityService = Mockito.mock(FacilityService.class);
        EmailService emailService = Mockito.mock(EmailService.class);
        UserService userService = Mockito.mock(UserService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService, facilityService,
            emailService, userService);

        final Integer SLOT_ID = 100;
        final Integer BOOKING_ID = 500;
        final Integer USER_ID = 1000;
        final Integer FACILITY_ID = 1234;

        User user = new User();
        user.setId(USER_ID);
        user.setEmail("janedoe@dal.ca");

        Facility facility = new Facility();
        facility.setId(FACILITY_ID);
        facility.setName("GYM");
        facility.setApprovalRequired(true);

        Booking booking = new Booking();
        booking.setSlotId(SLOT_ID);
        booking.setUserId(USER_ID);
        booking.setId(BOOKING_ID);
        booking.setFacilityId(FACILITY_ID);
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusDays(1));

        Mockito.when(bookingRepo.getById(BOOKING_ID)).thenReturn(booking);
        Mockito.doNothing().when(slotService).releaseSeat(SLOT_ID);
        Mockito.doNothing().when(bookingRepo).setBookingStatus(BOOKING_ID, BookingStatus.REJECTED);
        Mockito.when(facilityService.getFacilityById(FACILITY_ID)).thenReturn(facility);
        Mockito.when(userService.getUserById(USER_ID)).thenReturn(user);

        bookingService.denyBooking(BOOKING_ID);

        Mockito.verify(userService, Mockito.times(1)).getUserById(USER_ID);
        Mockito.verify(slotService, Mockito.times(1)).releaseSeat(SLOT_ID);
        try{
            Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString());
        }
        catch(MessagingException e) {
            Assertions.fail();
        }
    }
}
