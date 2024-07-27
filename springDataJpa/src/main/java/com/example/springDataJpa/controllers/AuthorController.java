package com.example.springDataJpa.controllers;


import com.example.springDataJpa.domain.dto.AuthorDto;
import com.example.springDataJpa.domain.entities.AuthorEntity;
import com.example.springDataJpa.mappers.Mapper;
import com.example.springDataJpa.services.AuthorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author){

        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity saveAuthorEntity = authorService.createAuthor(authorEntity);


        return authorMapper.mapTo(saveAuthorEntity);

    }
}
