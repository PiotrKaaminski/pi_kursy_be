package com.pi.kursy.courses.buy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BuyCourseRepositoryImpl implements BuyCourseRepository {

    private final BuyCourseJpaRepository repository;
    private final BuyCourseCourseJpaRepository courseRepository;
    private final BuyCourseClientJpaRepository clientRepository;

    @Override
    public Optional<BuyCourseSnapshot.Course> findCourseById(String id) {
        return courseRepository.findById(id)
                .map(BuyCourseJpaEntity.Course::toSnapshot);
    }

    @Override
    public Optional<BuyCourseSnapshot.Client> findClientById(String id) {
        return clientRepository.findById(id)
                .map(BuyCourseJpaEntity.Client::toSnapshot);
    }

    @Override
    public boolean accessExists(String clientId, String courseId) {
        return repository.existsByClient_IdAndCourse_Id(clientId, courseId);
    }

    @Override
    public void save(BuyCourseSnapshot snapshot) {
        repository.save(BuyCourseJpaEntity.fromSnapshot(snapshot));
    }
}
