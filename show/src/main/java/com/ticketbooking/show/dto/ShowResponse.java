package com.ticketbooking.show.dto;

import com.ticketbooking.show.repository.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {
    private Long id;
    private Long movieId;
    private Long screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double basePrice;
    private ShowStatus showStatus;
}
