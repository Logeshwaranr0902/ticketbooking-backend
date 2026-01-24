package com.ticketbooking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long showId;
    private List<Long> showSeatIds;
    private Double totalAmount;
    private LocalDateTime bookingTime;
    private String status;
}
