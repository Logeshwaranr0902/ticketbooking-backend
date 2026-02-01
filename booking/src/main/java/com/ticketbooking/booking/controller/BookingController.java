package com.ticketbooking.booking.controller;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.dto.SeatResponse;
import com.ticketbooking.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> bookShow(@Valid @RequestBody BookingRequest request) {
        BookingResponse bookingResponse = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<List<SeatResponse>> cancelShow(@PathVariable Long bookingId) {

       List<SeatResponse>seatResponse = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(HttpStatus.OK).body(seatResponse);
    }
}