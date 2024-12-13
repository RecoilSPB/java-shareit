package ru.practicum.request.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;
import ru.practicum.ShareItApp;
import ru.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@ContextConfiguration(classes = ShareItApp.class)
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    private User requester;
    private User otherUser;

    @BeforeEach
    void setUp() {
        requester = userRepository.save(new User(null, "Requester", "requester@mail.com"));
        otherUser = userRepository.save(new User(null, "OtherUser", "otheruser@mail.com"));
    }

    @Test
    void findByRequestor_whenNoRequests_shouldReturnEmptyList() {
        List<Request> requests = requestRepository.findByRequestor(requester);

        assertTrue(requests.isEmpty(), "Should return empty list when there are no requests");
    }

    @Test
    void findByRequestor_whenRequestsExist_shouldReturnRequests() {
        Request request1 = requestRepository.save(new Request(null, "Request 1",
                requester, LocalDateTime.now().minusDays(1)));
        Request request2 = requestRepository.save(new Request(null, "Request 2",
                requester, LocalDateTime.now()));

        List<Request> requests = requestRepository.findByRequestor(requester);

        assertEquals(2, requests.size(), "Should return all requests");
        assertTrue(requests.contains(request1), "Should contain request1");
        assertTrue(requests.contains(request2), "Should contain request2");
    }

    @Test
    void findByRequestorNot_whenNoRequests_shouldReturnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Request> requests = requestRepository.findByRequestorNot(requester, pageable);

        assertTrue(requests.isEmpty(), "Should return empty list when there are no requests");
    }

    @Test
    void findByRequestorNot_whenRequestsExist_shouldReturnRequests() {
        Request request1 = requestRepository.save(new Request(null, "Request 1", otherUser,
                LocalDateTime.now().minusDays(1)));
        Request request2 = requestRepository.save(new Request(null, "Request 2", otherUser, LocalDateTime.now()));

        Pageable pageable = PageRequest.of(0, 10);
        List<Request> requests = requestRepository.findByRequestorNot(requester, pageable);

        assertEquals(2, requests.size(), "Should return all requests");
        assertTrue(requests.contains(request1), "Should contain request1");
        assertTrue(requests.contains(request2), "Should contain request2");
    }
}