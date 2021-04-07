package com.csci5308.g17.booking;

import java.util.List;

import javax.mail.MessagingException;

import com.csci5308.g17.auth.CurrentUserService;
import com.csci5308.g17.email.EmailService;
import com.csci5308.g17.email.IEmailService;
import com.csci5308.g17.slot.SlotFullException;
import com.csci5308.g17.user.IUserService;
import com.csci5308.g17.user.User;
import com.csci5308.g17.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/booking")
public class BookingController {

    private Logger logger = LoggerFactory.getLogger(BookingController.class);
    private IBookingService bookingService;
    private CurrentUserService currentUserService;
    private IEmailService emailService;
    private IUserService userService;
    final String CONFIRMATION_EMAIL_TEMPLATE = "Your booking from %s to %s is confirmed.";
    final String CANCEL_BOOKING_EMAIL_TEMPLATE = "Your booking from at %s has been cancelled.";

    public BookingController() {
        bookingService = BookingService.getInstance();
        currentUserService = CurrentUserService.getInstance();
        emailService = EmailService.getInstance();
        userService = UserService.getInstance();
    }

    @PostMapping("")
    public String createBooking(@RequestParam("slot_id") Integer slotId, Model model) {
        User currentUser = currentUserService.getCurrentUser();
        try {
            Booking booking = bookingService.createBooking(currentUser.getId(), slotId);
            emailService.sendEmail(currentUser.getEmail(),
                 String.format(CONFIRMATION_EMAIL_TEMPLATE,
                    booking.getStartTime().toString(), booking.getEndTime().toString())
                );
            logger.info("Booking confirmation email sent");
        }
        catch(SlotFullException e) {
            logger.info("Cannot create booking, seats full");
            model.addAttribute("message", "Cannot reseve seat. Slot is full.");
            return "errorpage";
        }
        catch(MessagingException e) {
            logger.error("Error sending booking confim email", e);
        }
        return "redirect:/booking";
    }

    @GetMapping("")
    public ModelAndView getBookings(@RequestParam(name="facility_id", required=false) Integer facilityId) {
        ModelAndView mav = new ModelAndView();
        List<Booking> bookings;
        User currentUser = currentUserService.getCurrentUser();
        Boolean isUserManager = currentUserService.isManager();
        // populate booking list according to the requesting user.
        // If manager, populate the bookings of the requested facilityId
        // else populate user's bookings
        if(currentUserService.isManager()) {
            bookings = bookingService.getFacilityBookings(facilityId);
        }
        else {
            bookings = bookingService.getUserBookings(currentUser.getId());
        }
        mav.addObject("bookings", bookings);
        mav.addObject("user", currentUser);
        mav.addObject("isManager", isUserManager);
        mav.setViewName("booking");
        return mav;
    }

    @PostMapping("/{bookingId}/cancel")
    public RedirectView cancelBooking(@PathVariable("bookingId") Integer bookingId, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.getById(bookingId);
        User bookingUser = userService.getUserById(booking.getUserId());
        bookingService.cancelBooking(booking.getId());
        try {
            emailService.sendEmail(bookingUser.getEmail(),
                String.format(CANCEL_BOOKING_EMAIL_TEMPLATE, booking.getStartTime().toString()));
            logger.info("Booking cancellation email sent");
        }
        catch(MessagingException e) {
            logger.error("Error sending booking cancel email", e);
        }
        Integer facilityId = booking.getFacilityId();
        redirectAttributes.addAttribute("facility_id", facilityId);
        return new RedirectView("/booking");
    }
}
