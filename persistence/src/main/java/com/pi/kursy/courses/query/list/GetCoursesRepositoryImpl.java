package com.pi.kursy.courses.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class GetCoursesRepositoryImpl implements GetCoursesRepository {

    private final GetCoursesJpaRepository repository;

    @Override
    public List<GetCoursesEntryDto> getAll() {
        return repository.findAll()
                .stream()
                .map(GetCoursesJpaEntity::toDto)
                .collect(Collectors.toList());
    }
}
