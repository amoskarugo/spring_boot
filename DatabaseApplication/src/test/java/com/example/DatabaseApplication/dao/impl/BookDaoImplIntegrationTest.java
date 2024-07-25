package com.example.DatabaseApplication.dao.impl;


import com.example.DatabaseApplication.TestDataUtil;
import com.example.DatabaseApplication.domain.Author;
import com.example.DatabaseApplication.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTest {

    private  final BookDaoImpl underTest;
    private final AuthorDaoImpl authorDao;

    @Autowired
    public BookDaoImplIntegrationTest(BookDaoImpl underTest, AuthorDaoImpl authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        Book book = TestDataUtil.createTestBookA();
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        book.setAuthorId(author.getId());

        underTest.create(book);

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);

        List<Book> results = underTest.find();

        assertThat(results).hasSize(3).containsExactly(bookA, bookB, bookC);

    }
    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        authorDao.create(author);
        underTest.create(book);

        book.setTitle("UPDATED");
        underTest.update(book.getIsbn(), book);

        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA();

        authorDao.create(author);

        book.setAuthorId(author.getId());

        underTest.create(book);
        underTest.delete(book.getIsbn());

        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isEmpty();
    }
}
