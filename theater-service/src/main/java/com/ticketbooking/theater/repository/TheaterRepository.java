package com.ticketbooking.theater.repository;

import com.ticketbooking.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Long> {
    public List<Theater> findByCityIgnoreCase(String city);
}
