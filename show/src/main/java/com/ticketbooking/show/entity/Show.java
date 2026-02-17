package com.ticketbooking.show.entity;

import com.ticketbooking.show.repository.ShowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;   
    private Long screenId;  

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double basePrice;

    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<ShowSeat> showSeats;
}

