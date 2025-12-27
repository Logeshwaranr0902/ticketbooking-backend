package com.ticketbooking.movies.dto;

import com.ticketbooking.movies.entity.ContentRating;
import com.ticketbooking.movies.entity.Genre;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Min(1)

    private Integer durationMinutes;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate; // Uses java.time.LocalDate

    @NotNull(message = "Genre is required")
    private Genre genre;

    private ContentRating contentRating;
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;

    private String posterUrl;
}