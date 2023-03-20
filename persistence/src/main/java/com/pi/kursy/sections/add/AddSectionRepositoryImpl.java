package com.pi.kursy.sections.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class AddSectionRepositoryImpl implements AddSectionRepository {

    private final AddSectionCourseJpaRepository courseJpaRepository;
    private final AddSectionJpaRepository sectionJpaRepository;

    @Override
    public void save(AddSectionSnapshot snapshot) {
        sectionJpaRepository.save(AddSectionJpaEntity.fromSnapshot(snapshot));
    }

    @Override
    public Optional<AddSectionCourseSnapshot> findCourseById(String courseId) {
        return courseJpaRepository.findById(courseId)
                .map(course -> {
                    var sectionsCount = sectionJpaRepository.countByCourse_Id(courseId);
                    return course.toSnapshot(sectionsCount);
                });
    }
}
