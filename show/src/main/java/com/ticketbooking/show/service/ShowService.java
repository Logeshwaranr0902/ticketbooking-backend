package com.ticketbooking.show.service;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.dto.ShowRequest;
import com.ticketbooking.show.dto.ShowResponse;
import com.ticketbooking.show.entity.Show;
import com.ticketbooking.show.entity.ShowSeat;
import com.ticketbooking.show.exception.ResourceNotFoundException;
import com.ticketbooking.show.exception.SeatAlreadyBookedException;
import com.ticketbooking.show.feignClient.TheaterClient;
import com.ticketbooking.show.mapper.ShowMapper;
import com.ticketbooking.show.repository.ShowRepository;
import com.ticketbooking.show.repository.ShowSeatRepository;
import com.ticketbooking.show.repository.ShowStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final TheaterClient theaterClient;
    private final ShowMapper showMapper;

    @Transactional
    public ShowResponse createShow(ShowRequest request) {
        Show show = showMapper.toEntity(request);
        show.setShowStatus(ShowStatus.UPCOMING);

        Show savedShow = showRepository.save(show);

        List<SeatResponse> seats = theaterClient.getSeatsByScreenId(request.getScreenId());

        List<ShowSeat> showSeats = seats.stream().map(seat -> ShowSeat.builder()
                .seatId(seat.getId())
                .seatPosition(seat.getSeatPosition())
                .isBooked(false)
                .show(savedShow)
                .seatType(seat.getSeatType())
                .build()).toList();
        showSeatRepository.saveAll(showSeats);

        log.info("Show created with id: {} for movieId: {}", savedShow.getId(), request.getMovieId());
        return showMapper.toResponse(savedShow);
    }

    @Transactional(readOnly = true)
    public List<ShowResponse> getShowsByMovieId(Long movieId) {
        return showRepository.findByMovieId(movieId).stream()
                .map(showMapper::toResponse)
                .toList();
    }

    @Transactional
    public void deleteShow(Long showId) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found");
        }
        showRepository.deleteById(showId);
        log.info("Show deleted with id: {}", showId);
    }

    @Transactional(readOnly = true)
    public ShowResponse getShowById(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("No show found with id: " + showId));
        return showMapper.toResponse(show);
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatById(Long showId) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found with id: " + showId);
        }
        return showSeatRepository.findByShowId(showId).stream()
                .map(showMapper::toResponse)
                .toList();
    }

    @Transactional
    public List<SeatResponse> bookSeats(List<Long> seatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);

        if (seats.size() != seatIds.size()) {
            throw new ResourceNotFoundException("One or more seats not found");
        }

        List<ShowSeat> alreadyBooked = seats.stream()
                .filter(ShowSeat::isBooked)
                .toList();

        if (!alreadyBooked.isEmpty()) {
            String bookedIds = alreadyBooked.stream()
                    .map(s -> s.getId().toString())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            throw new SeatAlreadyBookedException("Seats already booked: " + bookedIds);
        }

        seats.forEach(seat -> seat.setBooked(true));
        showSeatRepository.saveAll(seats);

        log.info("Booked {} seats: {}", seats.size(), seatIds);
        return seats.stream().map(showMapper::toResponse).toList();
    }

    @Transactional
    public List<SeatResponse> cancelSeat(List<Long> seatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);
        seats.forEach(seat -> seat.setBooked(false));
        showSeatRepository.saveAll(seats);

        log.info("Cancelled {} seats: {}", seats.size(), seatIds);
        return seats.stream().map(showMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatsByIds(List<Long> seatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);
        return seats.stream().map(showMapper::toResponse).toList();
    }
}
