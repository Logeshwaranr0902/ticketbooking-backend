package com.ticketbooking.theater.mapper;

import com.ticketbooking.theater.dto.*;
import com.ticketbooking.theater.entity.Screen;
import com.ticketbooking.theater.entity.Seat;
import com.ticketbooking.theater.entity.SeatType;
import com.ticketbooking.theater.entity.Theater;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TheaterMapper {

    public Theater toEntity(TheaterRequest request){
        if(request==null) return null;
        Theater theater = Theater.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();

        List<Screen> screens = request.getScreens().stream()
                .map(screenReq -> toScreenEntity(screenReq, theater))
                .collect(Collectors.toList());

        theater.setScreens(screens);
        return theater;
    }

    private Screen toScreenEntity(ScreenRequest screenReq, Theater theater) {
        Screen screen = new Screen();
        screen.setName(screenReq.getName());
        screen.setScreenType(screenReq.getType());
        screen.setTheater(theater);

        // Generate Seats inside the Mapper

        return screen;
    }


    public TheaterResponse toResponse(Theater theater) {
        if (theater == null) return null;

        return TheaterResponse.builder()
                .id(theater.getId())
                .name(theater.getName())
                .city(theater.getCity())
                .address(theater.getAddress())
                // Map the list of screens
                .screens(theater.getScreens() != null ?
                        theater.getScreens().stream().map(this::toScreenResponse).toList() : null)
                .build();
    }

    private ScreenResponse toScreenResponse(Screen screen) {
        return ScreenResponse.builder()
                .id(screen.getId())
                .name(screen.getName())
                .screenType(screen.getScreenType())
                // Map the list of seats
                .seats(screen.getSeats() != null ?
                        screen.getSeats().stream().map(this::toSeatResponse).toList() : null)
                .build();
    }

    private SeatResponse toSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowNumber(seat.getRowNumber())
                .seatType(seat.getSeatType())
                .build();
    }
}
