package com.csci5308.g17.booking;

import org.springframework.stereotype.Service;

@Service
public class BookingService implements IBookingService {

    private static BookingService instance;
    private final IBookingRepository bookingRepository;

    public BookingService(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public static BookingService getInstance() {
        if (instance == null) {
            instance = new BookingService(BookingRepository.getInstance());
        }
        return instance;
    }


}
