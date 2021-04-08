package com.csci5308.g17.booking;

import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import com.csci5308.g17.email.EmailService;
import com.csci5308.g17.email.IEmailService;
import com.csci5308.g17.facility.Facility;
import com.csci5308.g17.facility.FacilityService;
import com.csci5308.g17.facility.IFacilityService;
import com.csci5308.g17.slot.ISlotService;
import com.csci5308.g17.slot.Slot;
import com.csci5308.g17.slot.SlotFullException;
import com.csci5308.g17.slot.SlotService;
import com.csci5308.g17.user.IUserService;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService {

    private Logger logger = LoggerFactory.getLogger(BookingService.class);
    private static BookingService instance;
    private IBookingRepository bookingRepository;
    private ISlotService slotService;
    private IFacilityService facilityService;
    private IEmailService emailService;
    private IUserService userService;
    private final String CONFIRMATION_EMAIL_TEMPLATE = "Your booking for %s from %s to %s is confirmed.";
    private final String CANCEL_BOOKING_EMAIL_TEMPLATE = "Your booking for %s at %s has been cancelled.";
    private final String APPROVED_BOOKING_EMAIL_TEMPLATE = "Your booking for %s at %s has been approved.";
    private final String DENIED_BOOKING_EMAIL_TEMPLATE = "Your booking for %s at %s has been denied.";

    public BookingService(IBookingRepository bookingRepository, ISlotService slotService,IFacilityService facilityService,
        IEmailService emailService, IUserService userService) {
        this.bookingRepository = bookingRepository;
        this.slotService = slotService;
        this.facilityService = facilityService;
        this.emailService = emailService;
        this.userService = userService;
    }

    public static BookingService getInstance() {
        if (instance == null) {
            instance = new BookingService(BookingRepository.getInstance(), SlotService.getInstance(),
                FacilityService.getInstance(), EmailService.getInstance(), UserService.getInstance());
        }
        return instance;
    }

    public Booking createBooking(Integer userId, Integer slotId) throws SlotFullException{

        Slot slot = slotService.getSlotById(slotId);
        User user = userService.getUserById(userId);

        Booking booking = new Booking();
        booking.setFacilityId(slot.getFacilityId());
        booking.setUserId(user.getId());
        booking.setSlotId(slot.getId());
        booking.setStartTime(slot.getStartTime());
        booking.setEndTime(slot.getEndTime());

        Facility facility = facilityService.getFacilityById(slot.getFacilityId());
        if (facility.getApprovalRequired()) {
            booking.setStatus(BookingStatus.APPROVAL_PENDING);
        }
        else {
            booking.setStatus(BookingStatus.CONFIRMED);
            String emailBody = String.format(CONFIRMATION_EMAIL_TEMPLATE,
                facility.getName(), booking.getStartTime().toString(), booking.getEndTime().toString());
            try{
                emailService.sendEmail(user.getEmail(), emailBody);
                logger.info("Booking confirmation email sent");
            }
            catch(MessagingException e) {
                logger.error("Error sending booking confim email", e);
            }
        }

        booking = bookingRepository.addBooking(booking);
        slotService.reserveSeat(booking.getSlotId());
        return booking;
    }

    public void cancelBooking(Integer bookingId) {
        Booking booking = getById(bookingId);
        Facility facility = facilityService.getFacilityById(booking.getFacilityId());
        User user = userService.getUserById(booking.getUserId());

        slotService.releaseSeat(booking.getSlotId());
        bookingRepository.setBookingStatus(bookingId, BookingStatus.CANCELLED);

        String emailBody = String.format(CANCEL_BOOKING_EMAIL_TEMPLATE,
            facility.getName(), booking.getStartTime().toString());
        try{
            emailService.sendEmail(user.getEmail(), emailBody);
            logger.info("Booking cancellation email sent");
        }
        catch(MessagingException e) {
            logger.error("Error sending booking cancellation email", e);
        }
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
        Booking booking = getById(bookingId);
        Facility facility = facilityService.getFacilityById(booking.getFacilityId());
        User user = userService.getUserById(booking.getUserId());

        String emailBody = String.format(APPROVED_BOOKING_EMAIL_TEMPLATE,
            facility.getName(), booking.getStartTime().toString());
        try{
            emailService.sendEmail(
                user.getEmail(),
                emailBody);
            logger.info("Booking approved email sent");
        }
        catch(MessagingException e) {
            logger.error("Error sending booking approved email", e);
        }

        bookingRepository.setBookingStatus(bookingId, BookingStatus.CONFIRMED);
    }

    public void denyBooking(Integer bookingId) {
        Booking booking = getById(bookingId);
        Facility facility = facilityService.getFacilityById(booking.getFacilityId());
        User user = userService.getUserById(booking.getUserId());

        slotService.releaseSeat(booking.getSlotId());
        bookingRepository.setBookingStatus(bookingId, BookingStatus.REJECTED);

        String emailBody = String.format(DENIED_BOOKING_EMAIL_TEMPLATE,
            facility.getName(), booking.getStartTime().toString());
        try{
            emailService.sendEmail(user.getEmail(), emailBody);
            logger.info("Booking denied email sent");
        }
        catch(MessagingException e) {
            logger.error("Error sending booking denied email", e);
        }
    }
}
