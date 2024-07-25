package com.example.springDataJpa;

import com.example.springDataJpa.domain.Author;
import com.example.springDataJpa.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){}


    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .age(80)
                .name("amos").build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .age(46)
                .name("william").build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .age(24)
                .name("lynne").build();
    }

    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("axw")
                .title("The mountain")
                .author(author).build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("abcd")
                .title("The river between")
                .author(author).build();
    }
    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("abcdef")
                .title("The greedy hyena")
                .author(author).build();
    }
}
