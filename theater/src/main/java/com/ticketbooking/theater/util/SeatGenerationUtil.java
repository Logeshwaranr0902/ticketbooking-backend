package com.ticketbooking.theater.util;

import com.ticketbooking.theater.entity.Screen;
import com.ticketbooking.theater.entity.Seat;
import com.ticketbooking.theater.entity.SeatType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SeatGenerationUtil {

    
    public static List<Seat> generateSeats(Screen screen, int rows, int seatsPerRow) {
        List<Seat> seats = new ArrayList<>();
        int segmentSize = Math.max(1, rows / 3);

        for (int row = 1; row <= rows; row++) {
            SeatType seatType = determineSeatType(row, segmentSize);

            for (int col = 1; col <= seatsPerRow; col++) {
                String rowLabel = getRowLabel(row);
                String seatPosition = String.format("%s%d", rowLabel, col);

                Seat seat = Seat.builder()
                        .rowNumber(row)
                        .seatPosition(seatPosition)
                        .seatType(seatType)
                        .screen(screen)
                        .build();
                seats.add(seat);
            }
        }

        return seats;
    }

    
    private static String getRowLabel(int rowNumber) {
        StringBuilder label = new StringBuilder();
        while (rowNumber > 0) {
            rowNumber--; 
            char currentChar = (char) ('A' + (rowNumber % 26));
            label.insert(0, currentChar);
            rowNumber /= 26;
        }
        return label.toString();
    }

    
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

