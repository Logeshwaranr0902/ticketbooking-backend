package com.ticketbooking.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long userId;
    private Long showId;

    @ElementCollection // Stores the list of ShowSeat IDs
    private List<Long> showSeatIds;

    private Double totalAmount;
    private LocalDateTime bookingTime;
    private String status; // PENDING, CONFIRMED, CANCELLED

}
