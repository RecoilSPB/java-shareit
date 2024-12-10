package ru.practicum.request.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.item.dto.ItemForRequestDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.exception.NotFoundObjectException;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;
import ru.practicum.item.storage.ItemRepository;
import ru.practicum.request.storage.RequestRepository;
import ru.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static ru.practicum.request.mapper.RequestMapper.toRequest;

@ExtendWith(MockitoExtension.class)
public class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @Test
    public void testAddRequest() {
        Long userId = 1L;
        RequestDto requestDto = RequestDto.builder()
                .description("Test Description")
                .build();

        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .build();

        Request request = toRequest(requestDto, user);
        request.setCreated(LocalDateTime.now());

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(requestRepository.save(any(Request.class))).thenReturn(request);

        RequestDto result = requestService.addRequest(userId, requestDto);

        assertEquals(request.getId(), result.getId());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getCreated(), result.getCreated());
    }

    @Test
    public void testGetRequestById() {
        Long userId = 1L;
        Long requestId = 1L;

        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .build();

        Request request = Request.builder()
                .id(requestId)
                .description("Test Description")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        List<ItemForRequestDto> answers = List.of();


        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(requestRepository.findById(eq(requestId))).thenReturn(Optional.of(request));
        when(itemRepository.findByRequest(eq(request))).thenReturn(List.of());

        RequestDto result = requestService.getRequestById(requestId, userId);

        assertEquals(request.getId(), result.getId());
        assertEquals(request.getDescription(), result.getDescription());
        assertEquals(request.getCreated(), result.getCreated());
        assertEquals(answers, result.getItems());
    }

    @Test
    public void testGetAllRequests() {
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;

        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .build();

        Request request1 = Request.builder()
                .id(1L)
                .description("Test Description 1")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        Request request2 = Request.builder()
                .id(2L)
                .description("Test Description 2")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        List<Request> requests = Arrays.asList(request1, request2);

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(requestRepository.findByRequestorNot(eq(user), any())).thenReturn(requests);
        when(itemRepository.findByRequest(any(Request.class))).thenReturn(List.of());

        List<RequestDto> result = requestService.getAllRequests(userId, from, size);

        assertEquals(2, result.size());
        assertEquals(request1.getId(), result.get(0).getId());
        assertEquals(request2.getId(), result.get(1).getId());
    }

    @Test
    public void testGetRequestsByUser() {
        Long userId = 1L;

        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .build();

        Request request1 = Request.builder()
                .id(1L)
                .description("Test Description 1")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        Request request2 = Request.builder()
                .id(2L)
                .description("Test Description 2")
                .created(LocalDateTime.now())
                .requestor(user)
                .build();

        List<Request> requests = Arrays.asList(request1, request2);

        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(requestRepository.findByRequestor(eq(user))).thenReturn(requests);
        // Mock items here
        when(itemRepository.findByRequest(any(Request.class))).thenReturn(List.of());

        List<RequestDto> result = requestService.getRequestsByUser(userId);

        assertEquals(2, result.size());
        assertEquals(request1.getId(), result.get(0).getId());
        assertEquals(request2.getId(), result.get(1).getId());
    }

    @Test
    public void testGetUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(eq(userId))).thenReturn(Optional.empty());

        assertThrows(NotFoundObjectException.class, () -> requestService.getRequestById(1L, 1L));
    }

}