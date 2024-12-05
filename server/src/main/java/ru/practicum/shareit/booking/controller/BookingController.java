package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.booking.BookingInputDto;
import ru.practicum.dto.booking.BookingOutputDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.state.BookingState;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingOutputDto createBooking(
            @Valid @RequestBody BookingInputDto bookingInputDto,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.createBooking(bookingInputDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingOutputDto approve(
            @PathVariable Long bookingId,
            @RequestParam @NotNull Boolean approved,
            @RequestHeader("X-Sharer-User-Id") Long userId
    ) {
        return bookingService.updateApprove(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingOutputDto getBookingInfo(@PathVariable Long bookingId,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingInfo(bookingId, userId);
    }

    @GetMapping
    public List<BookingOutputDto> getAllBookings(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL", required = false) BookingState state
    ) {
        return bookingService.getAllBookings(userId, state);
    }

    @GetMapping("owner")
    public List<BookingOutputDto> getAllBookingsForOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL", required = false) BookingState state
    ) {
        return bookingService.getAllBookingsForOwner(userId, state);
    }
}
