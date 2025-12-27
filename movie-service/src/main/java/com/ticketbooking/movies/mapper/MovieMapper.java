package com.ticketbooking.movies.mapper;

import com.ticketbooking.movies.dto.MovieRequest;
import com.ticketbooking.movies.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(MovieRequest request) {
        return Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .durationMinutes(request.getDurationMinutes())
                .language(request.getLanguage())      // Added
                .releaseDate(request.getReleaseDate()) // Added
                .genre(request.getGenre())
                .contentRating(request.getContentRating())
                .rating(request.getRating())
                .posterUrl(request.getPosterUrl())
                .build();
    }
}