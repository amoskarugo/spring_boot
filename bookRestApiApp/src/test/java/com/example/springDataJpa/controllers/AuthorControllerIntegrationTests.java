package com.example.springDataJpa.controllers;


import com.example.springDataJpa.TestDataUtil;
import com.example.springDataJpa.domain.dto.AuthorDto;
import com.example.springDataJpa.domain.entities.AuthorEntity;
import com.example.springDataJpa.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final AuthorService authorService;
    private final

    ObjectMapper objectMapper;
    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorEntity.setId(null);

        String authorJson = objectMapper.writeValueAsString(authorEntity);


        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsCreatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorEntity.setId(null);

        String authorJson = objectMapper.writeValueAsString(authorEntity);


        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("amos")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }
    @Test
    public void testThatListAuthorSuccessfullyReturnsHttpStatus200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorSuccessfullyReturnsListOfAuthors() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorService.save(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("amos")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }

    @Test
    public void testThatGetAuthorSuccessfullyReturnsHttpStatus200WhenAuthorExists() throws Exception {

        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorService.save(authorEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testThatGetAuthorSuccessfullyReturnsHttpStatus404WhenNoAuthorExists() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    public void testThatGetAuthorSuccessfullyReturnsAuthorWhenAuthorExists() throws Exception {

        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        authorService.save(authorEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("amos")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }


    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);



        mockMvc.perform(MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saveAuthorEntity = authorService.save(authorEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + saveAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyUpdatesWhenAuthorExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saveAuthorEntity = authorService.save(authorEntity);

        AuthorEntity authorDto = TestDataUtil.createTestAuthorB();
        authorDto.setId(saveAuthorEntity.getId());
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);



        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + saveAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(saveAuthorEntity.getId())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
                );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus200() throws Exception {

        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saveAuthorEntity = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");

        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/" + saveAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );

    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {

        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saveAuthorEntity = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("UPDATED");

        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/" + saveAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(saveAuthorEntity.getId())
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
                );

    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent()
                );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity saveAuthorEntity = authorService.save(authorEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/" + saveAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent()
                );
    }
}
