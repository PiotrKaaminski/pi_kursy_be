package com.pi.kursy.users.query.list;

import java.util.List;

record GetUsersResponseDto(
        List<GetUsersEntryDto> rows
) {
}
