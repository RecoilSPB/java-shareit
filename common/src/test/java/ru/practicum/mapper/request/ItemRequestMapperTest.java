package ru.practicum.mapper.request;

import org.junit.jupiter.api.Test;
import ru.practicum.dto.item.ItemForRequestDto;
import ru.practicum.dto.request.ItemRequestDto;
import ru.practicum.model.request.ItemRequest;
import ru.practicum.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRequestMapperTest {

    @Test
    void toItemRequestDto_withAnswers_shouldMapCorrectly() {
        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Test description")
                .created(LocalDateTime.now())
                .build();

        ItemForRequestDto answer = ItemForRequestDto.builder()
                .id(1L)
                .name("Item1")
                .description("Description1")
                .available(true)
                .requestId(itemRequest.getId())
                .build();
        List<ItemForRequestDto> answers = List.of(answer);

        ItemRequestDto result = ItemRequestMapper.toItemRequestDto(itemRequest, answers);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(itemRequest.getId());
        assertThat(result.getDescription()).isEqualTo(itemRequest.getDescription());
        assertThat(result.getItems()).isEqualTo(answers);
    }

    @Test
    void toItemRequest_shouldMapCorrectly() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .id(2L)
                .description("Another description")
                .created(LocalDateTime.now())
                .build();

        User user = User.builder().id(1L).name("Test User").build();

        ItemRequest result = ItemRequestMapper.toItemRequest(dto, user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        assertThat(result.getRequestor()).isEqualTo(user);
    }

    @Test
    void toItemRequestDto_withoutAnswers_shouldMapCorrectly() {
        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Test description")
                .created(LocalDateTime.now())
                .build();

        ItemRequestDto result = ItemRequestMapper.toItemRequestDto(itemRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(itemRequest.getId());
        assertThat(result.getDescription()).isEqualTo(itemRequest.getDescription());
        assertThat(result.getItems()).isNull();
    }
}
