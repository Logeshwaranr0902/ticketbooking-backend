package com.ticketbooking.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Seat IDs are required")
    private List<Long> seatIds;

    @NotNull(message = "Amount is required")
    private Double amount;
}