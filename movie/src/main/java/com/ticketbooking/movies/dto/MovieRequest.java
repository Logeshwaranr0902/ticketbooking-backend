package com.ticketbooking.movies.dto;

import com.ticketbooking.movies.entity.ContentRating;
import com.ticketbooking.movies.entity.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Content rating is required")
    private ContentRating contentRating;

    @NotBlank(message = "Poster URL is required")
    private String posterUrl;
}