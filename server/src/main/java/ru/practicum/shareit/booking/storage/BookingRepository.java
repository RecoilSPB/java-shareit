package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.booking.Booking;
import ru.practicum.model.booking.BookingStatus;
import ru.practicum.model.item.Item;
import ru.practicum.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker(User booker);

    List<Booking> findByBookerAndEndIsBefore(User booker, LocalDateTime end);

    List<Booking> findByBookerAndStartIsAfter(User booker, LocalDateTime start);

    @Query("select b from Booking b where b.booker = :booker and b.start <= current_timestamp and b.end >= current_timestamp")
    List<Booking> findCurrentByBooker(User booker);

    List<Booking> findByBookerAndStatus(User booker, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds")
    List<Booking> findAllByItemIds(List<Long> itemIds);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds AND b.start <= CURRENT_TIMESTAMP AND b.end >= CURRENT_TIMESTAMP")
    List<Booking> findCurrentByItemIds(List<Long> itemIds);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds AND b.end < CURRENT_TIMESTAMP")
    List<Booking> findPastByItemIds(List<Long> itemIds);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds AND b.start > CURRENT_TIMESTAMP")
    List<Booking> findFutureByItemIds(List<Long> itemIds);

    @Query("SELECT b FROM Booking b WHERE b.item.id IN :itemIds AND b.status = :status")
    List<Booking> findByItemIdsAndStatus(List<Long> itemIds, BookingStatus status);

    List<Booking> findByBookerAndItem(User booker, Item item);
}