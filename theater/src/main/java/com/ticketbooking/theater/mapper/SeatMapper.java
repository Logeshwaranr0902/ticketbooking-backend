package com.ticketbooking.theater.mapper;

import com.ticketbooking.theater.dto.SeatResponse;
import com.ticketbooking.theater.entity.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    SeatResponse toResponse(Seat seat);
}
