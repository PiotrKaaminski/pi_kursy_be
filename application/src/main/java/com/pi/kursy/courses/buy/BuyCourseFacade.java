package com.pi.kursy.courses.buy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BuyCourseFacade {

    private final BuyCourseRepository repository;
    private final BuyCourseFactory factory;

    void buyCourse(BuyCourseDto dto) throws Exception {
        var entity = factory.create(dto);
        var snapshot = entity.buy();
        repository.save(snapshot);
    }
}
