package com.ticketbooking.movies.dto;

import com.ticketbooking.movies.entity.ContentRating;
import com.ticketbooking.movies.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private String language;
    private LocalDate releaseDate;
    private Genre genre;
    private ContentRating contentRating;
    private Double rating;
    private String posterUrl;
}
