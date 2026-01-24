package com.ticketbooking.booking.service;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.entity.Booking;
import com.ticketbooking.booking.mapper.BookingMapper;
import com.ticketbooking.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest request) {
        Booking booking = bookingMapper.toEntity(request);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }
}