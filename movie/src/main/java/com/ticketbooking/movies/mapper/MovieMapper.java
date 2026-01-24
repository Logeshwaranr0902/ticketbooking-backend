package com.ticketbooking.movies.mapper;

import com.ticketbooking.movies.dto.MovieRequest;
import com.ticketbooking.movies.dto.MovieResponse;
import com.ticketbooking.movies.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true) // Rating is initially null or 0
    Movie toEntity(MovieRequest request);

    MovieResponse toResponse(Movie movie);
}