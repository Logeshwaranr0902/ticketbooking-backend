package com.ticketbooking.theater.mapper;

import com.ticketbooking.theater.dto.TheaterRequest;
import com.ticketbooking.theater.dto.TheaterResponse;
import com.ticketbooking.theater.entity.Theater;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = { ScreenMapper.class })
public interface TheaterMapper {

        
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "screens", ignore = true)
        Theater toEntity(TheaterRequest request);

        
        @Mapping(target = "screens", source = "screens")
        TheaterResponse toResponse(Theater theater);
}

