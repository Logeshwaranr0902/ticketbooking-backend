package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.entity.SeatType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private Integer rowNumber;
    private SeatType seatType;
}