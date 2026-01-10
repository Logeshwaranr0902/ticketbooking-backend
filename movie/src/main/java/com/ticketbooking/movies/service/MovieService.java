package com.ticketbooking.movies.service;


import com.querydsl.core.types.Predicate;
import com.ticketbooking.movies.dto.MovieRequest;
import com.ticketbooking.movies.entity.Movie;
import com.ticketbooking.movies.exception.ResourceNotFoundException;
import com.ticketbooking.movies.mapper.MovieMapper;
import com.ticketbooking.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public Movie saveMovie(MovieRequest movieRequest) {
        log.info("Saving new movie : {}", movieRequest.getTitle());

        // 1. Convert DTO to Entity
        Movie movieEntity = movieMapper.toEntity(movieRequest);

        // 2. Save and capture the RESULT (this has the generated ID from MySQL)
        Movie savedMovie = movieRepository.save(movieEntity);

        // 3. Return the saved object so the Controller can show the ID to the user
        return savedMovie;
    }

    public List<Movie>getAllMovie(){
        log.info("Fetching all movies from the database");
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        log.info("Searching for movie with ID: {}", id);
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie", "id", id);
        }
        log.warn("Deleting movie with ID: {}", id);
        movieRepository.deleteById(id);
    }
    @Transactional
    public Movie updateMovieById(Long id, MovieRequest movieRequest){
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));

        Movie updatedMovie = movieMapper.toEntity(movieRequest);
        updatedMovie.setId(existingMovie.getId()); // ✅ Preserve the ID!

        return movieRepository.save(updatedMovie); // Need save() here
    }

    public List<Long> findAllIdsByCriteria(Predicate predicate) {
        // 1. Fetch all matching movies
        Iterable<Movie> movies = movieRepository.findAll(predicate);

        // 2. Convert the Iterable of Movies into a List of IDs
        List<Long> ids = new ArrayList<>();
        movies.forEach(movie -> ids.add(movie.getId()));

        return ids;
    }
}
