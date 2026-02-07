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

    public BookingResponse createBooking(BookingRequest request, String userId) {
        List<SeatResponse> seatResponseList = showClient.bookSeats(request.getSeatIds());
        Booking booking = bookingMapper.toEntity(request);
        booking.setUserId(userId); // Set userId from JWT token
        Booking savedBooking = bookingRepository.save(booking);
        BookingResponse bookingResponse = bookingMapper.toResponse(savedBooking);
        bookingResponse.setSeats(seatResponseList);
        return bookingResponse;
    }

    public List<SeatResponse> cancelBooking(Long bookingId, String userId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            // Security check: Only allow cancelling own bookings
            if (!booking.getUserId().equals(userId)) {
                throw new RuntimeException("You can only cancel your own bookings");
            }

            List<Long> seatIds = booking.getShowSeatIds();
            List<SeatResponse> seatResponse = showClient.cancelSeats(seatIds);
            bookingRepository.deleteById(bookingId);
            return seatResponse;
        } else {
            throw new RuntimeException("Booking not found");
        }

    }

    public List<BookingResponse> getMyBookings(String userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking -> {
                    BookingResponse response = bookingMapper.toResponse(booking);
                    // Fetch seat details from ShowClient
                    List<SeatResponse> seats = showClient.getSeatsById(booking.getShowSeatIds());
                    response.setSeats(seats);
                    return response;
                })
                .toList();
    }

}