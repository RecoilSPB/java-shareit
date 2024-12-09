package ru.practicum.item.paging;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.exception.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testCreateWithValidParameters() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20);
        assertEquals(10, pageRequest.getOffset());
        assertEquals(20, pageRequest.getPageSize());
        assertEquals(Sort.unsorted(), pageRequest.getSort());
    }

    @Test
    void testCreateWithSort() {
        Sort sort = Sort.by("name").ascending();
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20, sort);
        assertEquals(10, pageRequest.getOffset());
        assertEquals(20, pageRequest.getPageSize());
        assertEquals(sort, pageRequest.getSort());
    }

    @Test
    void testCreateUnpaged() {
        Sort sort = Sort.by("name").ascending();
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.unpaged(sort);
        assertEquals(0, pageRequest.getOffset());
        assertEquals(Integer.MAX_VALUE, pageRequest.getPageSize());
        assertEquals(sort, pageRequest.getSort());
    }

    @Test
    void testCreateWithNullParameters() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(null, null);
        assertEquals(0, pageRequest.getOffset());
        assertEquals(Integer.MAX_VALUE, pageRequest.getPageSize());
        assertEquals(Sort.unsorted(), pageRequest.getSort());
    }

    @Test
    void testCreateWithInvalidParameters() {
        assertThrows(InvalidRequestException.class, () -> CustomPageRequest.create(10, null));
        assertThrows(InvalidRequestException.class, () -> CustomPageRequest.create(null, 20));
        assertThrows(InvalidRequestException.class, () -> CustomPageRequest.create(10, 0));
        assertThrows(InvalidRequestException.class, () -> CustomPageRequest.create(-1, 20));
    }

    @Test
    void testNext() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20);
        CustomPageRequest nextPageRequest = (CustomPageRequest) pageRequest.next();
        assertEquals(30, nextPageRequest.getOffset());
        assertEquals(20, nextPageRequest.getPageSize());
        assertEquals(Sort.unsorted(), nextPageRequest.getSort());
    }

    @Test
    void testPreviousOrFirst() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20);
        CustomPageRequest previousOrFirstPageRequest = (CustomPageRequest) pageRequest.previousOrFirst();
        assertEquals(10, previousOrFirstPageRequest.getOffset());
        assertEquals(20, previousOrFirstPageRequest.getPageSize());
        assertEquals(Sort.unsorted(), previousOrFirstPageRequest.getSort());
    }

    @Test
    void testFirst() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20);
        CustomPageRequest firstPageRequest = (CustomPageRequest) pageRequest.first();
        assertEquals(10, firstPageRequest.getOffset());
        assertEquals(20, firstPageRequest.getPageSize());
        assertEquals(Sort.unsorted(), firstPageRequest.getSort());
    }

    @Test
    void testWithPage() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20);
        CustomPageRequest withPageRequest = (CustomPageRequest) pageRequest.withPage(2);
        assertEquals(50, withPageRequest.getOffset());
        assertEquals(20, withPageRequest.getPageSize());
        assertEquals(Sort.unsorted(), withPageRequest.getSort());
    }

    @Test
    void testHasPrevious() {
        CustomPageRequest pageRequest = (CustomPageRequest) CustomPageRequest.create(10, 20);
        assertFalse(pageRequest.hasPrevious());
    }
}
