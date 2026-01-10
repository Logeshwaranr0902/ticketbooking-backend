package com.ticketbooking.show.dto;

import com.ticketbooking.show.dto.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SeatResponse {
    private Long id;
    private Long seatNumber;
    private Integer rowNumber;
    private SeatType seatType;
}