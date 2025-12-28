package com.ticketbooking.theater.controller;


import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.service.TheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(@Valid @RequestBody TheaterRequest request) {
        // Returns 201 Created because a new resource was added to the DB
        return new ResponseEntity<>(theaterService.createTheater(request), HttpStatus.CREATED);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<TheaterResponse>> getTheatersByCity(@PathVariable String city) {
        // Returns 200 OK
        return ResponseEntity.ok(theaterService.getAllTheatersByCity(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponse> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));

    }
}
