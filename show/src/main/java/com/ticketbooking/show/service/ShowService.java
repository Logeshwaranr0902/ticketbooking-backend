package com.ticketbooking.show.service;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.dto.ShowRequest;
import com.ticketbooking.show.entity.Show;
import com.ticketbooking.show.entity.ShowSeat;
import com.ticketbooking.show.feignClient.TheaterClient;
import com.ticketbooking.show.repository.ShowRepository;
import com.ticketbooking.show.repository.ShowSeatRepository;
import com.ticketbooking.show.repository.ShowStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final TheaterClient theaterClient;

    @Transactional // Ensures either everything is saved or nothing is (if an error occurs)
    public Show createShow(ShowRequest request) {
        // 1. Create and save the Show record
        Show show = Show.builder()
                .movieId(request.getMovieId())
                .screenId(request.getScreenId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .basePrice(request.getBasePrice())
                .showStatus(ShowStatus.UPCOMING)
                .build();

        Show savedShow = showRepository.save(show);




        List<SeatResponse>seats = theaterClient.getSeatsByScreenId(request.getScreenId());

        List<ShowSeat> showSeats = seats.stream().map(seat -> {
            // Logic to convert row number (e.g., 2) to Letter (e.g., 'B')
            char rowLetter = (char) ('A' + seat.getRowNumber() - 1);
            String formattedSeatNumber = rowLetter + String.valueOf(seat.getSeatNumber()); // Result: "B3"

            return ShowSeat.builder()
                    .seatId(seat.getId())
                    .seatNumber(formattedSeatNumber)
                    .isBooked(false)
                    .show(savedShow)
                    .build();
        }).toList();
        showSeatRepository.saveAll(showSeats);
        return savedShow;
    }

    public List<Show>getShowsByMovieId(Long movieId){
        return showRepository.findByMovieId(movieId);
    }
}