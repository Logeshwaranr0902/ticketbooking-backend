package com.ticketbooking.theater.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ticketbooking.theater.entity.ScreenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponse {
    private Long id;
    private String name;
    private ScreenType screenType;
    @JsonIgnore
    private List<SeatResponse> seats;
}