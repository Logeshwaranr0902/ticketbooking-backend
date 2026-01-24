package com.ticketbooking.theater.util;

import com.ticketbooking.theater.entity.Screen;
import com.ticketbooking.theater.entity.Seat;
import com.ticketbooking.theater.entity.SeatType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for seat generation logic.
 * Provides static methods for creating seat layouts with proper seat type
 * distribution.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SeatGenerationUtil {

    /**
     * Generates seats for a screen with proper seat type distribution.
     * 
     * @param screen      The screen to generate seats for
     * @param rows        Number of rows in the screen
     * @param seatsPerRow Number of seats per row
     * @return List of generated Seat entities
     */
    public static List<Seat> generateSeats(Screen screen, int rows, int seatsPerRow) {
        List<Seat> seats = new ArrayList<>();
        int segmentSize = Math.max(1, rows / 3);

        for (int row = 1; row <= rows; row++) {
            SeatType seatType = determineSeatType(row, segmentSize);

            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                Seat seat = Seat.builder()
                        .rowNumber(row)
                        .seatNumber((long) seatNum)
                        .seatType(seatType)
                        .screen(screen)
                        .build();
                seats.add(seat);
            }
        }

        return seats;
    }

    /**
     * Determines seat type based on row position.
     * First third: RECLINER (premium front seats)
     * Second third: PREMIUM (middle section)
     * Last third: REGULAR (back section)
     * 
     * @param row         The current row number (1-indexed)
     * @param segmentSize The size of each segment (rows / 3)
     * @return The appropriate SeatType for the row
     */
    public static SeatType determineSeatType(int row, int segmentSize) {
        if (row <= segmentSize) {
            return SeatType.RECLINER;
        } else if (row <= segmentSize * 2) {
            return SeatType.PREMIUM;
        } else {
            return SeatType.REGULAR;
        }
    }
}
