package com.example.springDataJpa.controllers;


import com.example.springDataJpa.domain.dto.AuthorDto;
import com.example.springDataJpa.domain.entities.AuthorEntity;
import com.example.springDataJpa.mappers.Mapper;
import com.example.springDataJpa.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){

        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity saveAuthorEntity = authorService.save(authorEntity);


        return new ResponseEntity<>(authorMapper.mapTo(saveAuthorEntity), HttpStatus.CREATED);

    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();

        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){

        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto,HttpStatus.OK);
                }).orElse(
                        new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){

        if(!authorService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        authorDto.setId(id);

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);

        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(authorEntity), HttpStatus.OK);

    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthorEntity = authorService.partialUpdate(id, authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
