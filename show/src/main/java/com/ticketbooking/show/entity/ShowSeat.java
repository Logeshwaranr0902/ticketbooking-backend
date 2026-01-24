package com.ticketbooking.show.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seatId;
    private String seatNumber;

    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonIgnore
    private Show show;
}