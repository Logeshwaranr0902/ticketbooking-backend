package com.ticketbooking.booking.mapper;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING") 
    @Mapping(target = "bookingTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "showSeatIds", source = "seatIds")
    @Mapping(target = "totalAmount", source = "amount")
    Booking toEntity(BookingRequest request);

    @Mapping(target = "seats", ignore = true)
    BookingResponse toResponse(Booking booking);
}

