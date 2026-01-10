package com.ticketbooking.movies.controller;

import com.querydsl.core.types.Predicate;
import com.ticketbooking.movies.dto.MovieRequest;
import com.ticketbooking.movies.entity.Movie;
import com.ticketbooking.movies.service.MovieService;
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
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        Movie savedMovie = movieService.saveMovie(movieRequest);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovie();
        return new ResponseEntity<>(movies,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String,List<Long>>> getMovieIds(
            @QuerydslPredicate(root = Movie.class) Predicate predicate) {

        List<Long> ids = movieService.findAllIdsByCriteria(predicate);
        Map<String,List<Long>>idss = new HashMap<>();
        idss.put("id",ids);
        return ResponseEntity.ok(idss);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable Long id,@Valid @RequestBody MovieRequest movieRequest){
        Movie movie = movieService.updateMovieById(id,movieRequest);
        return new ResponseEntity<>(movie,HttpStatus.OK);
    }
}