package com.pi.kursy.users.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class GetUsersRepositoryImpl implements GetUsersRepository {

    private final GetUsersJpaRepository repository;

    @Override
    public GetUsersResponseDto getAll(GetUsersFilters filters) {
        var entities = repository.findAll(getSpecification(filters));
        return new GetUsersResponseDto(
                entities.stream().map(GetUsersJpaEntity::toDto).toList()
        );
    }

    private Specification<GetUsersJpaEntity> getSpecification(GetUsersFilters filters) {
        Specification<GetUsersJpaEntity> specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        if (filters == null) {
            return specification;
        }
        if (filters.role() != null) {
            specification = specification.and(
                    (root, query, cb) -> cb.equal(root.get("role"), filters.role())
            );
        }
        return specification;
    }
}
