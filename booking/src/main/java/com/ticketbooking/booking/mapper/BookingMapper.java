package com.ticketbooking.booking.mapper;

import com.ticketbooking.booking.dto.BookingRequest;
import com.ticketbooking.booking.dto.BookingResponse;
import com.ticketbooking.booking.entity.Booking;
import com.ticketbooking.booking.entity.BookingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { BookingStatus.class })
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", expression = "java(BookingStatus.PENDING)")
    @Mapping(target = "bookingTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "showSeatIds", source = "seatIds")
    @Mapping(target = "totalAmount", source = "amount")
    Booking toEntity(BookingRequest request);

    @Mapping(target = "seats", ignore = true)
    BookingResponse toResponse(Booking booking);
}
