package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.dto.ScreenResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TheaterResponse {
    private Long id;
    private String name;
    private String city;
    private String address;
    private List<ScreenResponse> screens;
}