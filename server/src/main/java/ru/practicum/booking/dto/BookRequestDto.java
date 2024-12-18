package ru.practicum.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private long itemId;
    private LocalDateTime start;
    private LocalDateTime end;
}