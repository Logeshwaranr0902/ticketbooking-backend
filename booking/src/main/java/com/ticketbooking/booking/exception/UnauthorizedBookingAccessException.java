package com.ticketbooking.booking.exception;

public class UnauthorizedBookingAccessException extends RuntimeException {

    public UnauthorizedBookingAccessException() {
        super("You can only cancel your own bookings");
    }
}
