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
public class Author {

    private String name;

    private Integer age;

    private Long id;
}
