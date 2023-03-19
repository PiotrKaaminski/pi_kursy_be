package com.pi.kursy.courses.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UpdateCourseRepositoryImpl implements UpdateCourseRepository {

    private final UpdateCourseJpaRepository courseJpaRepository;
    private final UpdateCourseTeacherJpaRepository teacherJpaRepository;
    private final UpdateCourseCategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<UpdateCourseTeacherSnapshot> findTeacherById(String id) {
        return teacherJpaRepository.findById(id)
                .map(UpdateCourseTeacherJpaEntity::toSnapshot);
    }

    @Override
    public boolean categoryDoesntExistById(String id) {
        return !categoryJpaRepository.existsById(id);
    }

    @Override
    public void save(UpdateCourseSnapshot snapshot) {
        courseJpaRepository.save(UpdateCourseJpaEntity.fromSnapshot(snapshot));
    }

    @Override
    public Optional<UpdateCourseSnapshot> findById(String id) {
        return courseJpaRepository.findById(id).map(UpdateCourseJpaEntity::toSnapshot);
    }
}
