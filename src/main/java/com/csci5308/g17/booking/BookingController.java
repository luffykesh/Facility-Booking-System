package com.csci5308.g17.booking;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final IBookingService bookingService;


    public BookingController() {
        bookingService = BookingService.getInstance();
    }
}
