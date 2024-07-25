package com.example.DatabaseApplication.dao;

import com.example.DatabaseApplication.domain.Author;
import com.example.DatabaseApplication.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void create(Book book);

    Optional<Book> findOne(String s);

    List<Book> find();

    void update(String isbn, Book book);
}
