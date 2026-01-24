package com.ticketbooking.theater.mapper;

import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Theater;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for Theater entity.
 * Contains only pure mapping logic - no business logic.
 * Screen and seat creation with business logic is handled in TheaterService.
 */
@Mapper(componentModel = "spring", uses = { ScreenMapper.class })
public interface TheaterMapper {

        /**
         * Basic mapping - does NOT include screens.
         * Screen creation with seat generation is handled in TheaterService.
         */
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "screens", ignore = true)
        Theater toEntity(TheaterRequest request);

        /**
         * Maps Theater entity to response DTO including all nested screens and seats.
         */
        @Mapping(target = "screens", source = "screens")
        TheaterResponse toResponse(Theater theater);
}
