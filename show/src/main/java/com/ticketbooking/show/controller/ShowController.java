package com.ticketbooking.show.controller;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.dto.ShowRequest;
import com.ticketbooking.show.dto.ShowResponse;
import com.ticketbooking.show.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody ShowRequest request) {

        ShowResponse createdShow = showService.createShow(request);
        return new ResponseEntity<>(createdShow, HttpStatus.CREATED);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovieId(movieId));
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<String> deleteShow(@PathVariable Long showId) {
        showService.deleteShow(showId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/getshows/{showId}")
    public ResponseEntity<ShowResponse> getShow(@PathVariable Long showId) {
        ShowResponse showResponse = showService.getShowById(showId);
        return new ResponseEntity<>(showResponse, HttpStatus.OK);
    }

    @GetMapping("/getseats/{showId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByShow(@PathVariable Long showId) {
        List<SeatResponse> seats = showService.getSeatById(showId);
        return ResponseEntity.status(HttpStatus.OK).body(seats);

    }

    @PutMapping("/seats/book")
    public ResponseEntity<List<SeatResponse>> bookSeats(@RequestBody List<Long> seatIds) {
        return ResponseEntity.status(HttpStatus.OK).body(showService.bookSeats(seatIds));
    }

    @PutMapping("/seats/cancel")
    public ResponseEntity<List<SeatResponse>> cancelSeats(@RequestBody List<Long> seatIds) {
        return ResponseEntity.status(HttpStatus.OK).body(showService.cancelSeat(seatIds));
    }
}