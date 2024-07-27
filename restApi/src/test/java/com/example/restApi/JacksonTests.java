package com.example.restApi;

import com.example.restApi.domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JacksonTests {



    @Test
    public void testThatObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = Book.builder()
                .isbn("isbn-301-n-11-00s")
                .title("kifo kisimani")
                .author("amos karugo")
                .yearPublished("2014").build();


        String result = objectMapper.writeValueAsString(book);
        assertThat(result).isEqualTo("{\"isbn\":\"isbn-301-n-11-00s\",\"author\":\"amos karugo\",\"title\":\"kifo kisimani\",\"yearPublished\":\"2014\"}");
    }

    @Test
    public void testThatObjectMapperCanCreateJavaObjectFromJsonObject() throws JsonProcessingException {
        String json = "{\"isbn\":\"isbn-301-n-11-00s\",\"author\":\"amos karugo\",\"title\":\"kifo kisimani\",\"yearPublished\":\"2014\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        Book book = Book.builder()
                .isbn("isbn-301-n-11-00s")
                .title("kifo kisimani")
                .author("amos karugo")
                .yearPublished("2014").build();

        Book result = objectMapper.readValue(json, Book.class);

        assertThat(result).isEqualTo(book);
    }
}
