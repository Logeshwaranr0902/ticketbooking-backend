package com.ticketbooking.theater.service;

import com.querydsl.core.types.Predicate;
import com.ticketbooking.theater.dto.ScreenRequest;
import com.ticketbooking.theater.dto.SeatResponse;
import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Screen;
import com.ticketbooking.theater.entity.Seat;
import com.ticketbooking.theater.entity.Theater;
import com.ticketbooking.theater.exception.ResourceNotFoundException;
import com.ticketbooking.theater.mapper.ScreenMapper;
import com.ticketbooking.theater.mapper.SeatMapper;
import com.ticketbooking.theater.mapper.TheaterMapper;
import com.ticketbooking.theater.repository.ScreenRepository;
import com.ticketbooking.theater.repository.SeatRepository;
import com.ticketbooking.theater.util.SeatGenerationUtil;
import com.ticketbooking.theater.repository.TheaterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing theaters, screens, and seats.
 * Contains all business logic related to theater operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;
    private final ScreenMapper screenMapper;
    private final SeatMapper seatMapper;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    /**
     * Creates a new theater with screens and generated seats.
     *
     * @param request The theater creation request
     * @return The created theater response
     */
    @Transactional
    public TheaterResponse createTheater(TheaterRequest request) {
        log.info("Creating a new theater: {} in city: {}", request.getName(), request.getCity());

        // 1. Create base theater entity
        Theater theater = theaterMapper.toEntity(request);

        // 2. Create screens with seats (business logic in service layer)
        if (request.getScreens() != null && !request.getScreens().isEmpty()) {
            List<Screen> screens = createScreensWithSeats(request.getScreens(), theater);
            theater.setScreens(screens);
        }

        // 3. Save the Theater (Cascading saves Screens and Seats automatically)
        Theater savedTheater = theaterRepository.save(theater);

        log.info("Theater created successfully with ID: {}", savedTheater.getId());

        // 4. Transform back to Response DTO
        return theaterMapper.toResponse(savedTheater);
    }

    /**
     * Creates screens with generated seats for a theater.
     * 
     * @param screenRequests List of screen requests
     * @param theater        The parent theater
     * @return List of Screen entities with seats
     */
    private List<Screen> createScreensWithSeats(List<ScreenRequest> screenRequests, Theater theater) {
        List<Screen> screens = new ArrayList<>();

        for (ScreenRequest screenRequest : screenRequests) {
            // Map screen entity
            Screen screen = screenMapper.toEntity(screenRequest);
            screen.setTheater(theater);

            // Generate seats using SeatGenerationUtil
            List<Seat> seats = SeatGenerationUtil.generateSeats(
                    screen,
                    screenRequest.getRows(),
                    screenRequest.getSeatsPerRow());
            screen.setSeats(seats);

            screens.add(screen);
        }

        return screens;
    }

    /**
     * Retrieves all theaters in a given city.
     *
     * @param city The city name
     * @return List of theater responses
     */
    @Transactional(readOnly = true)
    public List<TheaterResponse> getAllTheatersByCity(String city) {
        log.info("Fetching all theaters in city: {}", city);

        List<Theater> theaters = theaterRepository.findByCityIgnoreCase(city);

        if (theaters.isEmpty()) {
            log.warn("No theaters found in city: {}", city);
            throw new ResourceNotFoundException("Theater", "city", city);
        }

        return theaters.stream()
                .map(theaterMapper::toResponse)
                .toList();
    }

    /**
     * Retrieves a theater by its ID.
     *
     * @param id The theater ID
     * @return The theater response
     */
    @Transactional(readOnly = true)
    public TheaterResponse getTheaterById(Long id) {
        log.info("Fetching theater with ID: {}", id);
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater", "id", id));

        return theaterMapper.toResponse(theater);
    }

    /**
     * Finds all theater IDs matching the given criteria.
     *
     * @param predicate The search predicate
     * @return List of theater IDs
     */
    @Transactional(readOnly = true)
    public List<Long> findAllIdsByCriteria(Predicate predicate) {
        Iterable<Theater> theaters = theaterRepository.findAll(predicate);

        List<Long> ids = new ArrayList<>();
        theaters.forEach(theater -> ids.add(theater.getId()));
        return ids;
    }

    /**
     * Retrieves all seats for a given screen.
     *
     * @param screenId The screen ID
     * @return List of seat responses
     */
    @Transactional(readOnly = true)
    public List<SeatResponse> getSeatsByScreenId(Long screenId) {
        log.info("Fetching seats for screen ID: {}", screenId);
        List<Seat> seats = seatRepository.findByScreenId(screenId);

        return seats.stream()
                .map(seatMapper::toResponse)
                .toList();
    }

    /**
     * Creates a seat layout for an existing screen.
     *
     * @param screenId The screen ID
     * @param rows     Number of rows
     * @param columns  Number of seats per row
     * @return Number of seats created
     */
    @Transactional
    public int createSeatLayout(Long screenId, int rows, int columns) {
        log.info("Creating seat layout for screen ID: {} with {} rows and {} columns", screenId, rows, columns);

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen", "id", screenId));

        // Use SeatGenerationUtil for seat creation
        List<Seat> seats = SeatGenerationUtil.generateSeats(screen, rows, columns);

        seatRepository.saveAll(seats);
        log.info("Created {} seats for screen ID: {}", seats.size(), screenId);

        return seats.size();
    }
}