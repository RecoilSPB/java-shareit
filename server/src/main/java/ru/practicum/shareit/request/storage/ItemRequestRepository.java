package ru.practicum.shareit.request.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.request.ItemRequest;
import ru.practicum.model.user.User;

import java.util.List;


public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findByRequestor(User requestor);

    List<ItemRequest> findByRequestorNot(User requestor, Pageable pageable);
}