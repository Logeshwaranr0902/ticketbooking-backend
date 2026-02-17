package com.ticketbooking.theater.mapper;

import com.ticketbooking.theater.dto.ScreenRequest;
import com.ticketbooking.theater.dto.ScreenResponse;
import com.ticketbooking.theater.entity.Screen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = { SeatMapper.class })
public interface ScreenMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "theater", ignore = true)
    @Mapping(target = "seats", ignore = true)
    Screen toEntity(ScreenRequest request);

    @Mapping(target = "seats", source = "seats")
    ScreenResponse toResponse(Screen screen);
}

