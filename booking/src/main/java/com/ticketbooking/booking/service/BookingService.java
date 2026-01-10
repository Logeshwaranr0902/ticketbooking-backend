package com.ticketbooking.booking.service;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.entity.Booking;
import com.ticketbooking.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(BookingRequest request) {
        // Logic: Just save the booking record for now
        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .showId(request.getShowId())
                .showSeatIds(request.getSeatIds())
                .totalAmount(request.getAmount())
                .status("PENDING")
                .bookingTime(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }
}