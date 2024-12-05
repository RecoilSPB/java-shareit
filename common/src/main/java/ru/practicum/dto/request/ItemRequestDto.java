package ru.practicum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.practicum.dto.item.ItemForRequestDto;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequestDto {
    private Long id;
    @NotBlank(message = "Отсутствует описание обьекта, которое вы ищите")
    private String description;
    private LocalDateTime created;
    private List<ItemForRequestDto> items;
}
