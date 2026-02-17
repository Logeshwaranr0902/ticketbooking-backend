package com.ticketbooking.movies.service;

import com.ticketbooking.movies.dto.MovieResponse;
import com.ticketbooking.movies.entity.Movie;
import com.ticketbooking.movies.exception.ResourceNotFoundException;
import com.ticketbooking.movies.mapper.MovieMapper;
import com.ticketbooking.movies.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository; 

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieService movieService; 

    private Movie mockMovie;
    private MovieResponse mockResponse;

    @BeforeEach
    void setUp() {
        
        mockMovie = Movie.builder()
                .id(1L)
                .title("The Dark Knight")
                .genre(com.ticketbooking.movies.entity.Genre.ACTION)
                .build();

        mockResponse = MovieResponse.builder()
                .id(1L)
                .title("The Dark Knight")
                .genre(com.ticketbooking.movies.entity.Genre.ACTION)
                .build();
    }

    @Test
    @DisplayName("Test 1: Success - Should return movie when ID exists")
    void getMovieById_Success() {
        
        when(movieRepository.findById(1L)).thenReturn(Optional.of(mockMovie));
        when(movieMapper.toResponse(mockMovie)).thenReturn(mockResponse);

        
        com.ticketbooking.movies.dto.MovieResponse result = movieService.getMovieById(1L);

        
        assertNotNull(result);
        assertEquals("The Dark Knight", result.getTitle());
        verify(movieRepository, times(1)).findById(1L); 
    }

    @Test
    @DisplayName("Test 2: Failure - Should throw Exception when ID not found")
    void getMovieById_NotFound() {
        
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        
        assertThrows(ResourceNotFoundException.class, () -> {
            movieService.getMovieById(99L);
        });
    }
}
