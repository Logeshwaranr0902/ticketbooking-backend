package com.ticketbooking.show.service;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.dto.ShowRequest;
import com.ticketbooking.show.dto.ShowResponse;
import com.ticketbooking.show.entity.Show;
import com.ticketbooking.show.entity.ShowSeat;
import com.ticketbooking.show.exception.ResourceNotFoundException;

import com.ticketbooking.show.feignClient.TheaterClient;
import com.ticketbooking.show.mapper.ShowMapper;
import com.ticketbooking.show.repository.ShowRepository;
import com.ticketbooking.show.repository.ShowSeatRepository;
import com.ticketbooking.show.repository.ShowStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        List<ShowSeat> showSeats = seats.stream().map(seat -> {
            return ShowSeat.builder()
                    .seatId(seat.getId())
                    .seatPosition(seat.getSeatPosition())
                    .isBooked(false)
                    .show(savedShow)
                    .seatType(seat.getSeatType())
                    .build();
        }).toList();
        showSeatRepository.saveAll(showSeats);

        return showMapper.toResponse(savedShow);
    }

    public List<ShowResponse> getShowsByMovieId(Long movieId) {
        return showRepository.findByMovieId(movieId).stream()
                .map(showMapper::toResponse)
                .toList();
    }

    public void deleteShow(Long showId) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found");
        }
        showRepository.deleteById(showId);
    }

    public ShowResponse getShowById(Long showId) {
        Optional<Show> shows = showRepository.findById(showId);
        return shows.stream().map((showMapper::toResponse)).findAny()
                .orElseThrow(() -> new ResourceNotFoundException("No show found with id: " + showId));

    }

    public List<SeatResponse> getSeatById(Long showId) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found with id: " + showId);
        }
        return showSeatRepository.findByShowId(showId).stream().map(showMapper::toResponse).toList();
    }

    public List<SeatResponse> bookSeats(List<Long> seatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);
        seats.forEach(seat -> seat.setBooked(true));
        showSeatRepository.saveAll(seats);
        return seats.stream().map(showMapper::toResponse).toList();
    }

    public List<SeatResponse> cancelSeat(List<Long> seatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);
        seats.forEach(seat -> seat.setBooked(false));
        showSeatRepository.saveAll(seats);
        return seats.stream().map(showMapper::toResponse).toList();
    }

    public List<SeatResponse> getSeatsByIds(List<Long> seatIds) {
        List<ShowSeat> seats = showSeatRepository.findAllById(seatIds);
        return seats.stream().map(showMapper::toResponse).toList();
    }
}
