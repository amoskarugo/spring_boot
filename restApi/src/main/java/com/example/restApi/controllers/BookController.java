package com.example.restApi.controllers;


import com.example.restApi.domain.Book;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
public class BookController {


    @GetMapping("/books")
    public Book retrieveBooks(){
        return Book.builder()
                .isbn("isbn-301-n-11-00s")
                .title("kifo kisimani")
                .author("amos karugo")
                .yearPublished("2014").build();
    }

    @PostMapping(path = "/books")
    public Book createBooks(@RequestBody Book book){
        log.info("got book " + book.toString());

        return book;
    }
}
