package com.example.springDataJpa.repositories;

import com.example.springDataJpa.TestDataUtil;
import com.example.springDataJpa.domain.entities.AuthorEntity;
import com.example.springDataJpa.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepoIntegrationTest {
    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorEntityRepoIntegrationTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }





    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorRepository.save(authorEntity);
        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);


    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        authorRepository.save(authorEntityC);

        Iterable<AuthorEntity> results = authorRepository.findAll();

        assertThat(results).hasSize(3).containsExactly(authorEntityA, authorEntityB, authorEntityC);

    }
    @Test
    public void testThatAuthorCanBeUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorRepository.save(authorEntity);

        authorEntity.setName("UPDATED");
        authorRepository.save(authorEntity);
        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorEntity);

        authorRepository.deleteById(authorEntity.getId());

        Optional<AuthorEntity> results = authorRepository.findById(authorEntity.getId());

        assertThat(results).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        authorRepository.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        authorRepository.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();

        authorRepository.save(authorEntityC);

        Iterable<AuthorEntity> result = authorRepository.ageLessThan(50);

        assertThat(result).containsExactly(authorEntityB, authorEntityC);
    }
}
