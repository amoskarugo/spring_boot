package com.example.springDataJpa.repositories;

import com.example.springDataJpa.TestDataUtil;
import com.example.springDataJpa.domain.Author;
import com.example.springDataJpa.repository.AuthorRepository;
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
public class AuthorRepoIntegrationTest {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorRepoIntegrationTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }





    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();

        authorRepository.save(author);
        Optional<Author> result = authorRepository.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);


    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        authorRepository.save(authorC);

        Iterable<Author> results = authorRepository.findAll();

        assertThat(results).hasSize(3).containsExactly(authorA, authorB, authorC);

    }
    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();

        authorRepository.save(author);

        author.setName("UPDATED");
        authorRepository.save(author);
        Optional<Author> result = authorRepository.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);

        authorRepository.deleteById(author.getId());

        Optional<Author> results = authorRepository.findById(author.getId());

        assertThat(results).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        Author authorA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();

        authorRepository.save(authorC);

        Iterable<Author> result = authorRepository.ageLessThan(50);

        assertThat(result).containsExactly(authorB, authorC);
    }
}
