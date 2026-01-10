package com.ticketbooking.theater.service;

import com.querydsl.core.types.Predicate;
import com.ticketbooking.theater.dto.SeatResponse;
import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Screen;
import com.ticketbooking.theater.entity.Seat;
import com.ticketbooking.theater.entity.SeatType;
import com.ticketbooking.theater.entity.Theater;
import com.ticketbooking.theater.exception.ResourceNotFoundException;
import com.ticketbooking.theater.mapper.TheaterMapper;
import com.ticketbooking.theater.repository.ScreenRepository;
import com.ticketbooking.theater.repository.SeatRepository;
import com.ticketbooking.theater.repository.TheaterRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ticketbooking.theater.entity.SeatType.*;

@Service
@Slf4j // For professional logging
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    @Transactional // Ensures atomicity: If seat generation fails, nothing is saved.
    public TheaterResponse createTheater(TheaterRequest request) {
        log.info("Creating a new theater: {} in city: {}", request.getName(), request.getCity());

        // 1. Transform DTO to Entity (Mapper handles the complex seat generation)
        Theater theater = theaterMapper.toEntity(request);

        // 2. Save the Theater (Cascading saves Screens and Seats automatically)
        Theater savedTheater = theaterRepository.save(theater);

        log.info("Theater created successfully with ID: {}", savedTheater.getId());

        // 3. Transform back to Response DTO
        return theaterMapper.toResponse(savedTheater);
    }

    @Transactional(readOnly = true)
    public List<TheaterResponse> getAllTheatersByCity(String city) {
        log.info("Fetching all theaters in city: {}", city);

        List<Theater> theaters = theaterRepository.findByCityIgnoreCase(city);

        // Check if the list is empty
        if (theaters.isEmpty()) {
            log.error("No theaters found in city: {}", city);
            throw new ResourceNotFoundException("Theater","city",city);
        }

        return theaters.stream()
                .map(theaterMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TheaterResponse getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater","id",id));

        return theaterMapper.toResponse(theater);
    }

    public List<Long> findAllIdsByCriteria(Predicate predicate) {
        // 1. Fetch all matching theater
        Iterable<Theater> theaters = theaterRepository.findAll(predicate);

        // 2. Convert the Iterable of theater into a List of IDs
        List<Long> ids = new ArrayList<>();
        theaters.forEach(theater -> ids.add(theater.getId()));
        return ids;
    }
    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatsByScreenId(Long screenId) {
        List<Seat> seats = seatRepository.findByScreenId(screenId);
        List<SeatResponse>seatss = seats.stream().map(seat -> new SeatResponse(seat.getId(), seat.getSeatNumber(),seat.getRowNumber(),seat.getSeatType())).toList();
        return seatss;
    }

    public void createSeatLayout(Long screenId,int rows,int cols) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new EntityNotFoundException("Screen not found"));
        List<Seat>seats = new ArrayList<>();

        for(int i=1;i<=rows;i++){
            SeatType seatType = null;
            int num = rows/3;

            if(i/num==0){
                seatType=RECLINER;
            }else if(i/num==1){
                seatType= PREMIUM;
            }else{
                seatType=REGULAR;
            }
            for(int j=1;j<=cols;j++){

                Seat seat = Seat.builder()
                        .rowNumber(i)
                        .seatNumber((long) j)
                        .seatType(seatType)
                        .screen(screen)
                        .build();

                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }
}