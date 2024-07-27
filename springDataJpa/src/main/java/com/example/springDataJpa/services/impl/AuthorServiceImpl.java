package com.example.springDataJpa.services.impl;

import com.example.springDataJpa.domain.entities.AuthorEntity;
import com.example.springDataJpa.repository.AuthorRepository;
import com.example.springDataJpa.services.AuthorService;
import org.springframework.stereotype.Service;


@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
