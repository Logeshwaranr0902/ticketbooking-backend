package com.ticketbooking.theater.controller;


import com.querydsl.core.types.Predicate;
import com.ticketbooking.theater.dto.SeatResponse;
import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Seat;
import com.ticketbooking.theater.entity.Theater;
import com.ticketbooking.theater.service.TheaterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/search")
    public ResponseEntity<Map<String,List<Long>>> getTheaterIds(
            @QuerydslPredicate(root = Theater.class) Predicate predicate) {

        List<Long> ids = theaterService.findAllIdsByCriteria(predicate);
        Map<String,List<Long>>idss = new HashMap<>();
        idss.put("id",ids);
        return ResponseEntity.ok(idss);
    }

    @GetMapping("{screenId}/seats/count")
    public ResponseEntity<List<SeatResponse>>getSeats(@PathVariable Long screenId){
        List<SeatResponse>seats= theaterService.getSeatsByScreenId(screenId);
        return ResponseEntity.ok(seats);
    }

    @PostMapping("{screenId}/createLayout")
    public ResponseEntity<String>createSeats(@PathVariable Long screenId,@RequestParam int rows,@RequestParam int col){
        theaterService.createSeatLayout(screenId,rows,col);
        return ResponseEntity.ok("Created");
    }

}
