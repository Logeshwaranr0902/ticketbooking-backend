package com.ticketbooking.booking.service;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.dto.SeatResponse;
import com.ticketbooking.booking.entity.Booking;
import com.ticketbooking.booking.exception.BookingNotFoundException;
import com.ticketbooking.booking.exception.UnauthorizedBookingAccessException;
import com.ticketbooking.booking.feignClient.ShowClient;
import com.ticketbooking.booking.mapper.BookingMapper;
import com.ticketbooking.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ShowClient showClient;

    @Transactional
    public BookingResponse createBooking(BookingRequest request, String userId) {
        log.info("Creating booking for userId: {}, showId: {}", userId, request.getShowId());

        List<SeatResponse> seatResponseList = showClient.bookSeats(request.getSeatIds());

        Booking booking = bookingMapper.toEntity(request);
        booking.setUserId(userId);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created with id: {}", savedBooking.getId());

        BookingResponse bookingResponse = bookingMapper.toResponse(savedBooking);
        bookingResponse.setSeats(seatResponseList);

        return bookingResponse;
    }

    @Transactional
    public List<SeatResponse> cancelBooking(Long bookingId, String userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (!booking.getUserId().equals(userId)) {
            throw new UnauthorizedBookingAccessException();
        }

        List<Long> seatIds = booking.getShowSeatIds();
        List<SeatResponse> seatResponse = showClient.cancelSeats(seatIds);
        bookingRepository.deleteById(bookingId);

        log.info("Booking {} cancelled by user {}", bookingId, userId);
        return seatResponse;
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings(String userId) {
        log.info("Fetching bookings for userId: {}", userId);

        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(booking -> {
                    BookingResponse response = bookingMapper.toResponse(booking);
                    List<SeatResponse> seats = showClient.getSeatsById(booking.getShowSeatIds());
                    response.setSeats(seats);
                    return response;
                })
                .toList();
    }
}
