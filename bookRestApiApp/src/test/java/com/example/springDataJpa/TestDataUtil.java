package com.example.springDataJpa;

import com.example.springDataJpa.domain.dto.AuthorDto;
import com.example.springDataJpa.domain.dto.BookDto;
import com.example.springDataJpa.domain.entities.AuthorEntity;
import com.example.springDataJpa.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil(){}


    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(1L)
                .age(80)
                .name("amos").build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(1L)
                .age(80)
                .name("amos").build();
    }


    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .age(46)
                .name("william").build();
    }
    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .age(24)
                .name("lynne").build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("axw")
                .title("The mountain")
                .authorEntity(authorEntity).build();
    }
    public static BookDto createTestBookDtoA(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("axw")
                .title("The mountain")
                .author(authorDto).build();
    }


    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("abcd")
                .title("The river between")
                .authorEntity(authorEntity).build();
    }
    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("abcdef")
                .title("The greedy hyena")
                .authorEntity(authorEntity).build();
    }
}
