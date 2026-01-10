package com.ticketbooking.booking.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
    private Double amount;
}