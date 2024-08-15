package com.example.springDataJpa.controllers;


import com.example.springDataJpa.domain.dto.BookDto;
import com.example.springDataJpa.domain.entities.BookEntity;
import com.example.springDataJpa.mappers.Mapper;
import com.example.springDataJpa.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {


    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;



    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);

        BookEntity savedBook = bookService.createUpdateBook(isbn, bookEntity);

        BookDto savedBookDto = bookMapper.mapTo(savedBook);

        if(bookExists){
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }

    }

    @GetMapping("/books")
    public List<BookDto> listBooks(){
        List<BookEntity> books = bookService.findAll();

        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/books/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") String isbn){
        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto ,HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        if(!bookService.isExists(isbn))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(updatedBookEntity), HttpStatus.OK);
    }


    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteAuthor(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
