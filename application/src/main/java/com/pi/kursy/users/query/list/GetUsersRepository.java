package com.pi.kursy.users.query.list;

interface GetUsersRepository {
    GetUsersResponseDto getAll(GetUsersFilters filters);
}
