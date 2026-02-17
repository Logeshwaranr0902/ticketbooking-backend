package com.ticketbooking.booking.service;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.dto.SeatResponse;
import com.ticketbooking.booking.entity.Booking;
import com.ticketbooking.booking.feignClient.ShowClient;
import com.ticketbooking.booking.mapper.BookingMapper;
import com.ticketbooking.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ShowClient showClient;

    public BookingResponse createBooking(BookingRequest request, String userId) {
        log.info(">>> createBooking START - userId: {}, seatIds: {}", userId, request.getSeatIds());

        log.info(">>> Calling showClient.bookSeats...");
        List<SeatResponse> seatResponseList = showClient.bookSeats(request.getSeatIds());
        log.info(">>> showClient.bookSeats DONE - got {} seats", seatResponseList.size());

        Booking booking = bookingMapper.toEntity(request);
        booking.setUserId(userId);

        log.info(">>> Saving booking to DB...");
        Booking savedBooking = bookingRepository.save(booking);
        log.info(">>> Booking saved - id: {}", savedBooking.getId());

        BookingResponse bookingResponse = bookingMapper.toResponse(savedBooking);
        bookingResponse.setSeats(seatResponseList);

        log.info(">>> createBooking END");
        return bookingResponse;
    }

    public List<SeatResponse> cancelBooking(Long bookingId, String userId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            
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
        log.info(">>> getMyBookings START - userId: {}", userId);

        List<Booking> bookings = bookingRepository.findByUserId(userId);
        log.info(">>> Found {} bookings in DB", bookings.size());

        List<BookingResponse> result = bookings.stream()
                .map(booking -> {
                    BookingResponse response = bookingMapper.toResponse(booking);
                    log.info(">>> Calling showClient.getSeatsById for booking {} with seatIds: {}", booking.getId(), booking.getShowSeatIds());
                    List<SeatResponse> seats = showClient.getSeatsById(booking.getShowSeatIds());
                    log.info(">>> showClient.getSeatsById DONE for booking {}", booking.getId());
                    response.setSeats(seats);
                    return response;
                })
                .toList();

        log.info(">>> getMyBookings END");
        return result;
    }

}
