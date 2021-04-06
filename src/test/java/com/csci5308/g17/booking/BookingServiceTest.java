package com.csci5308.g17.booking;

import java.time.LocalDateTime;

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
        BookingService bookingService = new BookingService(bookingRepo, slotService);
        Booking newBooking;

        final Integer USER_ID = 500;
        final Integer SLOT_ID = 100;
        Slot slot = new Slot();
        slot.setId(100);
        slot.setStartTime(LocalDateTime.now());
        slot.setEndTime(LocalDateTime.now().plusHours(1));

        Mockito.when(slotService.getSlotById(SLOT_ID)).thenReturn(slot);
        try{
            Mockito.doNothing().when(slotService).reserveSeat(SLOT_ID);
        }
        catch(SlotFullException e){
            Assertions.fail();
        }

        Mockito.when(bookingRepo.addBooking(Mockito.any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);
        try {
            newBooking = bookingService.createBooking(USER_ID, SLOT_ID);

            Mockito.verify(slotService, Mockito.times(1)).reserveSeat(SLOT_ID);

            Assertions.assertEquals(SLOT_ID, newBooking.getSlotId());
            Assertions.assertEquals(USER_ID, newBooking.getUserId());
            Assertions.assertEquals(BookingStatus.CONFIRMED, newBooking.getStatus());
        }
        catch(SlotFullException e){
            Assertions.fail();
        }
    }

    @Test
    public void cancelBookingTest() {
        BookingRepository bookingRepo = Mockito.mock(BookingRepository.class);
        SlotService slotService = Mockito.mock(SlotService.class);
        BookingService bookingService = new BookingService(bookingRepo, slotService);

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
}
