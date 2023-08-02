package com.pi.kursy.users.query.list;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
class GetUsersController {

    private final GetUsersFacade facade;

    @GetMapping
    GetUsersResponse getUsers(GetUsersFilters filters) {
        var responseDto = facade.getUsers(filters);
        return GetUsersResponse.fromDto(responseDto);
    }

    record GetUsersResponse(
            List<GetUsersEntry> rows
    ) {
        static GetUsersResponse fromDto(GetUsersResponseDto dto) {
            return new GetUsersResponse(
                    dto.rows().stream().map(GetUsersEntry::fromDto).toList()
            );
        }
    }

    record GetUsersEntry(
            String id,
            String username,
            RoleEnum role
    ) {
        static GetUsersEntry fromDto(GetUsersEntryDto dto) {
            return new GetUsersEntry(
                    dto.id(),
                    dto.username(),
                    dto.role()
            );
        }
    }


}
