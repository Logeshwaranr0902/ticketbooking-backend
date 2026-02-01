package com.ticketbooking.show.feignClient;

import com.ticketbooking.show.dto.SeatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "theater-service")
public interface TheaterClient {

    // Move the full path here where Feign can actually resolve the variable
    @GetMapping("/api/v1/theaters/{screenId}/seats")
    List<SeatResponse> getSeatsByScreenId(@PathVariable("screenId") Long screenId);
}