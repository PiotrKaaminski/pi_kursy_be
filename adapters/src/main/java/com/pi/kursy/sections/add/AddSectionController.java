package com.pi.kursy.sections.add;

import com.pi.kursy.security.configuration.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
class AddSectionController {

    private final AddSectionFacade facade;

    @PostMapping("/courses/{courseId}/sections")
    AddSectionResponse addSection(
            @RequestBody AddSectionRequest request,
            @PathVariable String courseId,
            UsernamePasswordAuthenticationToken authentication
    ) throws Exception {
        var teacherId = ((UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal()).getId();
        var responseDto = facade.addSection(request.toDto(courseId, teacherId));
        return AddSectionResponse.fromDto(responseDto);
    }

    record AddSectionRequest(
            String title
    ) {
        AddSectionDto toDto(String courseId, String teacherId) {
            return new AddSectionDto(title, courseId, teacherId);
        }
    }

    record AddSectionResponse(
            String id
    ) {
        static AddSectionResponse fromDto(AddSectionResponseDto dto) {
            return new AddSectionResponse(dto.id());
        }
    }
}
