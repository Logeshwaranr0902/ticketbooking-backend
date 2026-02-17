package com.ticketbooking.show.mapper;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.dto.ShowRequest;
import com.ticketbooking.show.dto.ShowResponse;
import com.ticketbooking.show.entity.Show;
import com.ticketbooking.show.entity.ShowSeat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShowMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "showStatus", ignore = true) 
    @Mapping(target = "showSeats", ignore = true)
    Show toEntity(ShowRequest request);

    SeatResponse toResponse(ShowSeat showSeat);

    ShowResponse toResponse(Show show);
}

