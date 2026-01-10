package com.ticketbooking.show.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequest {
    private Long movieId;
    private Long screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double basePrice;
}