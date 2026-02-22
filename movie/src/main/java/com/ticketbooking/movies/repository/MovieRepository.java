package com.ticketbooking.movies.repository;

import com.ticketbooking.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> , QuerydslPredicateExecutor<Movie> {

    Movie findByTitle(String name);
}
