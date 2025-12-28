package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.entity.Screen;
import com.ticketbooking.theater.entity.ScreenType;
import lombok.Data;

@Data
public class ScreenRequest {
    private String name;
    private ScreenType type; // 2D, 3D, IMAX
    private int rows;        // For seat generation
    private int seatsPerRow; // For seat generation
}