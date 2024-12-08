package ru.practicum.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.validation.CreateObject;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class DateBookingDto extends BaseBookingDto {
    @NotNull(message = "ID пользователя не может быть null", groups = CreateObject.class)
    private Long bookerId;
}