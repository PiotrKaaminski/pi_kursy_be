package com.pi.kursy.sections.add;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class AddSectionController {

    private final AddSectionFacade facade;

    @PostMapping("/courses/{courseId}/sections")
    AddSectionResponse addSection(@RequestBody AddSectionRequest request, @PathVariable String courseId) throws Exception {
        var responseDto = facade.addSection(request.toDto(courseId));
        return AddSectionResponse.fromDto(responseDto);
    }

    record AddSectionRequest(
            String title
    ) {
        AddSectionDto toDto(String courseId) {
            return new AddSectionDto(title, courseId);
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
