package com.example.DatabaseApplication.dao.impl;

import com.example.DatabaseApplication.TestDataUtil;
import com.example.DatabaseApplication.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {


    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;


    @Test
    public void testBookDaoImplGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);


        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("axw"), eq("The mountain"), eq(1L)
        );
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql(){
        underTest.findOne("axw");

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(), eq("axw"));
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql(){
        underTest.find();

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testThatBookUpdateGenerateCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.update(book.getIsbn(), book);

        verify(jdbcTemplate).update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                "axw", "The mountain", 1L, "axw");
    }
    @Test

    public void testThatUpdateGeneratesCorrectSql(){
        underTest.delete("axw");
        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ?",
                "axw");
    }
}
