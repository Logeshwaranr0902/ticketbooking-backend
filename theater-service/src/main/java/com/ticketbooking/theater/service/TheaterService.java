package com.ticketbooking.theater.service;

import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Theater;
import com.ticketbooking.theater.exception.ResourceNotFoundException;
import com.ticketbooking.theater.mapper.TheaterMapper;
import com.ticketbooking.theater.repository.TheaterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j // For professional logging
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;

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
}