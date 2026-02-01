package com.ticketbooking.show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatPosition;

    private SeatType seatType;
}