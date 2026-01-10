package com.ticketbooking.theater.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seatNumber;

    @Column(name="seat_row")
    private Integer rowNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType; // REGULAR, PREMIUM, RECLINER

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;



}
