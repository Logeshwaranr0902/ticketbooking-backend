package com.ticketbooking.movies.service;

import com.querydsl.core.types.Predicate;
import com.ticketbooking.movies.dto.MovieRequest;
import com.ticketbooking.movies.dto.MovieResponse;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieResponse saveMovie(MovieRequest movieRequest) {
        log.info("Saving new movie : {}", movieRequest.getTitle());
        Movie movieEntity = movieMapper.toEntity(movieRequest);
        Movie savedMovie = movieRepository.save(movieEntity);
        return movieMapper.toResponse(savedMovie);
    }

    public List<MovieResponse> getAllMovie() {
        log.info("Fetching all movies from the database");
        return movieRepository.findAll().stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    public MovieResponse getMovieById(Long id) {
        log.info("Searching for movie with ID: {}", id);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        return movieMapper.toResponse(movie);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movie", "id", id);
        }
        log.warn("Deleting movie with ID: {}", id);
        movieRepository.deleteById(id);
    }

    @Transactional
    public MovieResponse updateMovieById(Long id, MovieRequest movieRequest) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));

        Movie updatedMovie = movieMapper.toEntity(movieRequest);
        updatedMovie.setId(existingMovie.getId()); 

        Movie savedMovie = movieRepository.save(updatedMovie);
        return movieMapper.toResponse(savedMovie);
    }

    public List<Long> findAllIdsByCriteria(Predicate predicate) {
        
        Iterable<Movie> movies = movieRepository.findAll(predicate);

        
        List<Long> ids = new ArrayList<>();
        movies.forEach(movie -> ids.add(movie.getId()));

        return ids;
    }
}

