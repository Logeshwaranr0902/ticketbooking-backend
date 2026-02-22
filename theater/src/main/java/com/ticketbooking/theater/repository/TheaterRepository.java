package com.ticketbooking.theater.repository;

import com.ticketbooking.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long>, QuerydslPredicateExecutor<Theater> {
    List<Theater> findByCityIgnoreCase(String city);
}
