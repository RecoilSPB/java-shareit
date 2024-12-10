package ru.practicum.request.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequestor(User requestor);

    List<Request> findByRequestorNot(User requestor, Pageable pageable);
}