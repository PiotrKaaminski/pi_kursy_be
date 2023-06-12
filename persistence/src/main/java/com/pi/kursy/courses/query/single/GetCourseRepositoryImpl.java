package com.pi.kursy.courses.query.single;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class GetCourseRepositoryImpl implements GetCourseRepository {

    private final GetCourseJpaRepository repository;

    @Override
    public Optional<GetCourseSnapshot> findById(String id) {
        return repository.findById(id)
                .map(GetCourseJpaEntity::toSnapshot);
    }

    @Override
    public boolean userHasAccess(String courseId, String userId) {
        return repository.existsByIdAndUsersWithAccess_Id(courseId, userId);
    }
}
