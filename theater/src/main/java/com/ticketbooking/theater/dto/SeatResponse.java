package com.ticketbooking.theater.dto;

import com.ticketbooking.theater.entity.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private Long id;
    private Long seatNumber;
    private Integer rowNumber;
    private SeatType seatType;
}