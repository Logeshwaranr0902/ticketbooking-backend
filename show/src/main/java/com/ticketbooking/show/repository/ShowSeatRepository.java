package com.ticketbooking.show.repository;

import com.ticketbooking.show.dto.SeatResponse;
import com.ticketbooking.show.entity.Show;
import com.ticketbooking.show.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShowId(Long showId);
}
