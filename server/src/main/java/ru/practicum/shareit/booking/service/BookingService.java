package ru.practicum.shareit.booking.service;


import ru.practicum.dto.booking.BookingInputDto;
import ru.practicum.dto.booking.BookingOutputDto;
import ru.practicum.shareit.booking.state.BookingState;

import java.util.List;

public interface BookingService {
    BookingOutputDto createBooking(BookingInputDto bookingInputDto, Long userId);

    BookingOutputDto updateApprove(Long bookingId, Boolean approved, Long userId);

    BookingOutputDto getBookingInfo(Long bookingId, Long userId);

    List<BookingOutputDto> getAllBookings(Long bookerId, BookingState state, Integer from, Integer size);

    List<BookingOutputDto> getAllBookingsForOwner(Long ownerId, BookingState state, Integer from, Integer size);
}