package com.ticketbooking.show.controller;

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
}