package com.example.DatabaseApplication.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Configuration
@Builder
@Data
public class Book {

    private String isbn;
    private String title;
    private Long authorId;
}
