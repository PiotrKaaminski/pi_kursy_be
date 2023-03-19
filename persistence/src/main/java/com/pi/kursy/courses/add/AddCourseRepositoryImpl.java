package com.pi.kursy.courses.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class AddCourseRepositoryImpl implements AddCourseRepository {

    private final AddCourseJpaRepository courseJpaRepository;
    private final AddCourseTeacherJpaRepository teacherJpaRepository;
    private final AddCourseCategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<AddCourseTeacherSnapshot> findTeacherById(String id) {
        return teacherJpaRepository.findById(id)
                .map(AddCourseTeacherJpaEntity::toSnapshot);
    }

    @Override
    public boolean categoryDoesntExistById(String id) {
        return !categoryJpaRepository.existsById(id);
    }

    @Override
    public void save(AddCourseSnapshot snapshot) {
        courseJpaRepository.save(AddCourseJpaEntity.fromSnapshot(snapshot));
    }
}
