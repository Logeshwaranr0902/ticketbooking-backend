package com.ticketbooking.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheaterResponse {
    private Long id;
    private String name;
    private String city;
    private String address;
    private List<ScreenResponse> screens;
}