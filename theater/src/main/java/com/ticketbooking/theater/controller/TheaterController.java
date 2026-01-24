package com.ticketbooking.theater.controller;

import com.querydsl.core.types.Predicate;
import com.ticketbooking.theater.dto.SeatResponse;
import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Theater;
import com.ticketbooking.theater.service.TheaterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/theaters")
@RequiredArgsConstructor
@Validated // Enable validation on method parameters
public class TheaterController {

    private final TheaterService theaterService;

    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(@Valid @RequestBody TheaterRequest request) {
        return new ResponseEntity<>(theaterService.createTheater(request), HttpStatus.CREATED);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<TheaterResponse>> getTheatersByCity(@PathVariable String city) {
        return ResponseEntity.ok(theaterService.getAllTheatersByCity(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponse> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, List<Long>>> getTheaterIds(
            @QuerydslPredicate(root = Theater.class) Predicate predicate) {

        List<Long> ids = theaterService.findAllIdsByCriteria(predicate);
        Map<String, List<Long>> response = new HashMap<>();
        response.put("id", ids);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{screenId}/seats")
    public ResponseEntity<List<SeatResponse>> getSeats(@PathVariable Long screenId) {
        List<SeatResponse> seats = theaterService.getSeatsByScreenId(screenId);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("/{screenId}/seats/layout")
    public ResponseEntity<Map<String, Object>> createSeatLayout(
            @PathVariable Long screenId,
            @RequestParam @Min(value = 1, message = "Rows must be at least 1") @Max(value = 50, message = "Rows cannot exceed 50") int rows,
            @RequestParam @Min(value = 1, message = "Columns must be at least 1") @Max(value = 30, message = "Columns cannot exceed 30") int columns) {

        int seatsCreated = theaterService.createSeatLayout(screenId, rows, columns);

        Map<String, Object> response = Map.of(
                "message", "Seat layout created successfully",
                "screenId", screenId,
                "totalSeats", seatsCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
