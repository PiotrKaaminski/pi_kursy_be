package com.pi.kursy.courses.query.list;

import lombok.Data;

import java.util.List;

@Data
class GetCoursesFilters{
    Pagination pagination;
    Sort sort;
    List<String> categoryIds;
    PriceRange priceRange;

    @Data
    static class Pagination {
        Integer page;
        Integer size;
    }

    @Data
    static class Sort {
        Order order;
        SortBy by;

        enum Order {
            DESC, ASC
        }

        enum SortBy {
            PRICE
        }
    }

    @Data
    static class PriceRange {
        Float from;
        Float to;
    }
}
