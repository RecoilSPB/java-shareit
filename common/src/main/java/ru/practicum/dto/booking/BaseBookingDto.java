package ru.practicum.dto.booking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseBookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
}
