package ru.practicum.paging.item;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.exception.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomPageRequestTest {

    @Test
    void create_withValidOffsetAndSize_shouldCreatePageRequest() {
        Pageable pageable = CustomPageRequest.create(10, 5, Sort.by("name"));

        assertThat(pageable.getOffset()).isEqualTo(10);
        assertThat(pageable.getPageSize()).isEqualTo(5);
        assertThat(pageable.getSort().getOrderFor("name")).isNotNull();
    }

    @Test
    void create_withNegativeOffset_shouldThrowException() {
        assertThatThrownBy(() -> CustomPageRequest.create(-1, 5))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("from must be positive or 0");
    }

    @Test
    void create_withZeroSize_shouldThrowException() {
        assertThatThrownBy(() -> CustomPageRequest.create(0, 0))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("size must be positive");
    }

    @Test
    void create_withNullOffsetOnly_shouldThrowException() {
        assertThatThrownBy(() -> CustomPageRequest.create(null, 5))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("must provide both from and size or no one");
    }

    @Test
    void create_withNullSizeOnly_shouldThrowException() {
        assertThatThrownBy(() -> CustomPageRequest.create(5, null))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("must provide both from and size or no one");
    }

    @Test
    void next_shouldReturnNextPageRequest() {
        Pageable pageable = CustomPageRequest.create(10, 5);
        Pageable nextPage = pageable.next();

        assertThat(nextPage.getOffset()).isEqualTo(15);
        assertThat(nextPage.getPageSize()).isEqualTo(5);
    }

    @Test
    void previousOrFirst_shouldReturnSamePage() {
        Pageable pageable = CustomPageRequest.create(10, 5);
        Pageable previousPage = pageable.previousOrFirst();

        assertThat(previousPage.getOffset()).isEqualTo(10);
        assertThat(previousPage.getPageSize()).isEqualTo(5);
    }

    @Test
    void first_shouldReturnFirstPage() {
        Pageable pageable = CustomPageRequest.create(10, 5);
        Pageable firstPage = pageable.first();

        assertThat(firstPage.getOffset()).isEqualTo(10);
        assertThat(firstPage.getPageSize()).isEqualTo(5);
    }

    @Test
    void withPage_shouldCalculateCorrectOffset() {
        Pageable pageable = CustomPageRequest.create(10, 5);
        Pageable thirdPage = pageable.withPage(2);

        assertThat(thirdPage.getOffset()).isEqualTo(20);
        assertThat(thirdPage.getPageSize()).isEqualTo(5);
    }

    @Test
    void hasPrevious_shouldAlwaysReturnFalse() {
        Pageable pageable = CustomPageRequest.create(10, 5);
        assertThat(pageable.hasPrevious()).isFalse();
    }
}
