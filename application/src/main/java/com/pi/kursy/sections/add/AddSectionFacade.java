package com.pi.kursy.sections.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class AddSectionFacade {

    private final AddSectionRepository repository;
    private final AddSectionFactory factory;

    AddSectionResponseDto addSection(AddSectionDto dto) throws Exception {
        var entity = factory.create(dto);
        var snapshot = entity.save();
        repository.save(snapshot);
        return new AddSectionResponseDto(snapshot.id());
    }
}
