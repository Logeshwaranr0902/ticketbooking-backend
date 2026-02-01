package com.ticketbooking.booking.service;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.dto.SeatResponse;
import com.ticketbooking.booking.entity.Booking;
import com.ticketbooking.booking.feignClient.ShowClient;
import com.ticketbooking.booking.mapper.BookingMapper;
import com.ticketbooking.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ShowClient showClient;

    public BookingResponse createBooking(BookingRequest request) {
        List<SeatResponse> seatResponseList = showClient.bookSeats(request.getSeatIds());
        Booking booking = bookingMapper.toEntity(request);
        Booking savedBooking = bookingRepository.save(booking);
        BookingResponse bookingResponse = bookingMapper.toResponse(savedBooking);
        bookingResponse.setSeats(seatResponseList);
        return bookingResponse;
    }

    public List<SeatResponse> cancelBooking(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            List<Long> seatIds = booking.getShowSeatIds();
           List<SeatResponse>seatResponse = showClient.cancelSeats(seatIds);
            bookingRepository.deleteById(bookingId);
            return seatResponse;
        } else {
            throw new RuntimeException("Booking not found");
        }

    }

}