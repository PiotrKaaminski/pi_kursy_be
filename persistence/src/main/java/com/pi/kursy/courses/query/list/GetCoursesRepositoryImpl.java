package com.pi.kursy.courses.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class GetCoursesRepositoryImpl implements GetCoursesRepository {

    private final GetCoursesJpaRepository repository;

    @Override
    public GetCoursesResponseDto getAll(GetCoursesFilters filters) {
        var page =  repository.findAll(getSpecification(filters), getPagination(filters));

        return new GetCoursesResponseDto(
                page.get().map(GetCoursesJpaEntity::toDto).toList(),
                page.getTotalElements()
        );
    }

    private Specification<GetCoursesJpaEntity> getSpecification(GetCoursesFilters filters) {
        Specification<GetCoursesJpaEntity> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (filters.categoryIds != null && !filters.categoryIds.isEmpty()) {
            specification = specification.and(
                    ((root, query, criteriaBuilder) -> {
                        var categoriesJoin = root.join("categories");
                        return categoriesJoin.get("id").in(filters.categoryIds);
                    })
            );
        }
        if (filters.priceRange != null) {
            var priceRange = filters.priceRange;
            if (priceRange.from != null) {
                specification = specification.and(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceRange.from)
                );
            }
            if (priceRange.to!= null) {
                specification = specification.and(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceRange.to)
                );
            }
        }
        return specification;
    }

    private Pageable getPagination(GetCoursesFilters filters) {
        Sort sort;
        if (filters != null && filters.sort != null) {
            var sortFilter = filters.sort;
            sort = Sort.by(
                    sortFilter.order == null ? Sort.Direction.ASC : Sort.Direction.valueOf(sortFilter.order.name()),
                    sortFilter.by == null ? "price" : sortFilter.by.name().toLowerCase()
            );
        } else {
            sort = Sort.by(Sort.Direction.ASC, "price");
        }
        var pagination = filters == null ? null : filters.pagination;
        return PageRequest.of(
                pagination == null || pagination.page == null ? 0 : pagination.page,
                pagination == null || pagination.size == null ? 10 : pagination.size,
                sort
        );
    }
}
