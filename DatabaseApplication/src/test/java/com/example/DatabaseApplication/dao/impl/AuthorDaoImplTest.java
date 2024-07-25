package com.example.DatabaseApplication.dao.impl;

import com.example.DatabaseApplication.TestDataUtil;
import com.example.DatabaseApplication.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplTest {


    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;


    @Test
    public void testThatAuthorGeneratesCorrectSql(){

        Author author = TestDataUtil.createTestAuthorA();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("amos"), eq(80));
    }


    @Test
    public void testThatFindOneGeneratesCorrectSql(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L));
    }

    @Test
    public void testThatFindManyGeneratesTheCorrectSql(){
        underTest.find();

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors"),
        ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }
    @Test
    public void testThatUpdateGenerateCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                1L, "amos", 80, 1L);
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql(){
        underTest.delete(1L);
        verify(jdbcTemplate).update("DELETE FROM authors WHERE id = ?", 1L);
    }
}
