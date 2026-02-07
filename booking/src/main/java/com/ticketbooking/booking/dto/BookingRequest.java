package com.ticketbooking.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    // userId is NOT here - it comes from JWT token for security!

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Seat IDs are required")
    private List<Long> seatIds;

    @NotNull(message = "Amount is required")
    private Double amount;
}