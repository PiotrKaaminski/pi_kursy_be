package com.pi.kursy.users.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetUsersFacade {

    private final GetUsersRepository repository;

    GetUsersResponseDto getUsers(GetUsersFilters filters) {
        return repository.getAll(filters);
    }
}
