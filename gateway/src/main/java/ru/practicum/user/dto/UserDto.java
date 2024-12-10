package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.validation.CreateObject;
import ru.practicum.validation.UpdateObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @NotBlank(message = "Имя не может быть пустым", groups = {CreateObject.class})
    private String name;
    @Email(message = "Некорректный email", groups = {CreateObject.class, UpdateObject.class})
    @NotBlank(message = "email не может быть пустым", groups = {CreateObject.class})
    private String email;
}