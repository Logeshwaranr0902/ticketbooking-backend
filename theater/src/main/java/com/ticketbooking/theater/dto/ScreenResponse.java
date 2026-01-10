package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.dto.SeatResponse;
import com.ticketbooking.theater.entity.ScreenType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScreenResponse {
    private Long id;
    private String name;
    private ScreenType screenType;
    private List<SeatResponse> seats;
}