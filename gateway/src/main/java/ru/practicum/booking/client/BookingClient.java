package ru.practicum.booking.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.booking.dto.BookItemRequestDto;
import ru.practicum.client.BaseClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .build()
        );
    }

    public ResponseEntity<Object> getAllBookings(long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", state);
        parameters.put("size", size);
        parameters.put("from", from);
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> createBooking(long userId, BookItemRequestDto requestDto) {
        return post(userId, requestDto);
    }

    public ResponseEntity<Object> getBookingInfo(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> updateApprove(long bookingId, boolean approved, long userId) {
        return patch("/" + bookingId + "?approved=" + approved, userId);
    }

    public ResponseEntity<Object> getAllBookingsForOwner(long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", state);
        parameters.put("size", size);
        parameters.put("from", from);
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }
}