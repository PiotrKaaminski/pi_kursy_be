package com.pi.kursy.courses.query.single;

import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "courses")
class GetCourseJpaEntity {
    @Id
    private String id;
    private String name;
    private Float price;
    private String description;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Set<Section> sections;

    GetCourseResponseDto toDto() {
        return new GetCourseResponseDto(
                id,
                name,
                price,
                description,
                teacher.toDto(),
                categories.stream().map(Category::toDto).toList(),
                sections.stream().map(Section::toDto).toList()
        );
    }


    @Entity
    @Table(name = "users")
    private static class Teacher {
        @Id
        private String id;
        private String username;

        private GetCourseResponseDto.Teacher toDto() {
            return new GetCourseResponseDto.Teacher(
                    id,
                    username
            );
        }
    }

    @Entity
    @Table(name = "categories")
    private static class Category {
        @Id
        private String id;
        private String name;

        private GetCourseResponseDto.Category toDto() {
            return new GetCourseResponseDto.Category(
                    id,
                    name
            );
        }
    }

    @Entity
    @Table(name = "sections")
    private static class Section {
        @Id
        private String id;
        private String title;
        private Integer sequence;

        private GetCourseResponseDto.Section toDto() {
            return new GetCourseResponseDto.Section(
                    id,
                    title,
                    sequence
            );
        }
    }
}
