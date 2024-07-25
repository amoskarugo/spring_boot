package com.example.DatabaseApplication;

import com.example.DatabaseApplication.domain.Author;
import com.example.DatabaseApplication.domain.Book;

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
                .age(70)
                .name("william").build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .age(90)
                .name("lynne").build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("axw")
                .title("The mountain")
                .authorId(1L).build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("abcd")
                .title("The river between")
                .authorId(2L).build();
    }
    public static Book createTestBookC() {
        return Book.builder()
                .isbn("abcdef")
                .title("The greedy hyena")
                .authorId(3L).build();
    }
}
