package com.ticketbooking.booking.feignClient;

import com.ticketbooking.booking.dto.SeatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "show-service", url = "${SHOW_SERVICE_URL:}")
public interface ShowClient {

    @PutMapping("/api/v1/shows/seats/book")
    List<SeatResponse> bookSeats(@RequestBody List<Long> seatIds);

    @PutMapping("/api/v1/shows/seats/cancel")
    List<SeatResponse> cancelSeats(@RequestBody List<Long> seatIds);

    @PostMapping("/api/v1/shows/seats/by-ids")
    List<SeatResponse> getSeatsById(@RequestBody List<Long> seatIds);
}
