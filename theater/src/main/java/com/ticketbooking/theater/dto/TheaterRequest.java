package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.dto.ScreenRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
public class TheaterRequest {

    @NotBlank(message = "Name is required")
    private String name;
    private String city;
    private String address;
    private List<ScreenRequest> screens;
}