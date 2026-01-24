package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.entity.ScreenType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScreenRequest {

    @NotBlank(message = "Screen name is required")
    private String name;

    @NotNull(message = "Screen type is required")
    private ScreenType screenType; // 2D, 3D, IMAX

    @Min(value = 1, message = "Rows must be at least 1")
    @Max(value = 50, message = "Rows cannot exceed 50")
    private int rows;

    @Min(value = 1, message = "Seats per row must be at least 1")
    @Max(value = 30, message = "Seats per row cannot exceed 30")
    private int seatsPerRow;
}