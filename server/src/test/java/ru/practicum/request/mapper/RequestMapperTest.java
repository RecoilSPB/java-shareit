package ru.practicum.request.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.item.dto.ItemForRequestDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {

    @Test
    void toRequestDto_withAnswers_shouldMapCorrectly() {
        Request request = Request.builder()
                .id(1L)
                .description("Test description")
                .created(LocalDateTime.now())
                .build();

        ItemForRequestDto answer = ItemForRequestDto.builder()
                .id(1L)
                .name("Item1")
                .description("Description1")
                .available(true)
                .requestId(request.getId())
                .build();
        List<ItemForRequestDto> answers = List.of(answer);

        RequestDto result = RequestMapper.toRequestDto(request, answers);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(request.getId());
        assertThat(result.getDescription()).isEqualTo(request.getDescription());
        assertThat(result.getItems()).isEqualTo(answers);
    }

    @Test
    void toRequest_shouldMapCorrectly() {
        RequestDto dto = RequestDto.builder()
                .id(2L)
                .description("Another description")
                .created(LocalDateTime.now())
                .build();

        User user = User.builder().id(1L).name("Test User").build();

        Request result = RequestMapper.toRequest(dto, user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dto.getId());
        assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        assertThat(result.getRequestor()).isEqualTo(user);
    }

    @Test
    void toRequestDto_withoutAnswers_shouldMapCorrectly() {
        Request request = Request.builder()
                .id(1L)
                .description("Test description")
                .created(LocalDateTime.now())
                .build();

        RequestDto result = RequestMapper.toRequestDto(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(request.getId());
        assertThat(result.getDescription()).isEqualTo(request.getDescription());
        assertThat(result.getItems()).isNull();
    }
}
