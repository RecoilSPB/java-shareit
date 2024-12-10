package ru.practicum.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validation.CreateObject;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateBookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    @NotNull(message = "ID пользователя не может быть null", groups = CreateObject.class)
    private Long bookerId;
}