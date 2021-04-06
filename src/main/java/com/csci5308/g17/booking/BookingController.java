package com.csci5308.g17.booking;

import java.util.List;

import com.csci5308.g17.auth.CurrentUserService;
import com.csci5308.g17.slot.SlotFullException;
import com.csci5308.g17.user.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/booking")
public class BookingController {

    private IBookingService bookingService;
    private CurrentUserService currentUserService;

    public BookingController() {
        bookingService = BookingService.getInstance();
        currentUserService = CurrentUserService.getInstance();
    }

    @PostMapping("")
    public String createBooking(@RequestParam("slot_id") Integer slotId, Model model) {
        User currentUser = currentUserService.getCurrentUser();
        try{
            bookingService.createBooking(currentUser.getId(), slotId);
        }
        catch(SlotFullException e) {
            model.addAttribute("message", "Cannot reseve seat. Slot is full.");
            return "errorpage";
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
    public String cancelBooking(@PathVariable("bookingId") Integer bookingId, RedirectAttributes redirectAttributes) {
        Booking booking = bookingService.getById(bookingId);
        bookingService.cancelBooking(booking.getId());
        Integer facilityId = booking.getFacilityId();
        redirectAttributes.addAttribute("facility_id", facilityId);
        return "redirect:/booking";
    }
}
