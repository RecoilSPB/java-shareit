package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemForRequestDto {
    private Long id;
    String name;
    private String description;
    private Boolean available;
    private Long requestId;
}