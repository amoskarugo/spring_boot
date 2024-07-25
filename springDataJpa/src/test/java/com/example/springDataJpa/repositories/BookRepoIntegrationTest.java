package com.example.springDataJpa.repositories;

import com.example.springDataJpa.TestDataUtil;
import com.example.springDataJpa.domain.Author;
import com.example.springDataJpa.domain.Book;
import com.example.springDataJpa.repository.BookRepository;
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
public class BookRepoIntegrationTest {

    private  final BookRepository bookRepository;
    @Autowired
    public BookRepoIntegrationTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);


        bookRepository.save(book);

        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();

        Book bookA = TestDataUtil.createTestBookA(author);

        bookRepository.save(bookA);

        Book bookB = TestDataUtil.createTestBookB(author);
        bookRepository.save(bookB);

        Book bookC = TestDataUtil.createTestBookC(author);
        bookRepository.save(bookC);

        Iterable<Book> results = bookRepository.findAll();

        assertThat(results).hasSize(3).containsExactly(bookA, bookB, bookC);

    }
    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();

        Book book = TestDataUtil.createTestBookA(author);

        bookRepository.save(book);

        book.setTitle("UPDATED");
        bookRepository.save(book);

        Optional<Book> result = bookRepository.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);

        bookRepository.save(book);


        bookRepository.deleteById(book.getIsbn());

        Optional<Book> result = bookRepository.findById(book.getIsbn());

        assertThat(result).isEmpty();
    }
}
