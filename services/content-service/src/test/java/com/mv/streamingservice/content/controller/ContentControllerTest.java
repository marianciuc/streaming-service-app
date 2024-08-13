package com.mv.streamingservice.content.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mv.streamingservice.content.dto.request.ContentRequest;
import com.mv.streamingservice.content.dto.response.ContentResponse;
import com.mv.streamingservice.content.enums.ContentType;
import com.mv.streamingservice.content.enums.RecordStatus;
import com.mv.streamingservice.content.service.ContentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentService contentService;

    private static ObjectMapper mapper;

    @BeforeAll
    static void setup() {
        mapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
        contentService = null;
    }

    @Test
    void createContent() throws Exception {
        ContentRequest request = new ContentRequest(
                UUID.randomUUID(),
                "title",
                "TestContentDescription",
                ContentType.MOVIE,
                LocalDateTime.now(),
                RecordStatus.ACTIVE,
                120,
                "PG-13",
                "https://localhost:2200/testimage",
                new ArrayList<>(),
                UUID.randomUUID(),
                new ArrayList<>()
        );
        UUID generatedId = UUID.randomUUID();
        given(contentService.createContent(request)).willReturn(generatedId);

        mockMvc.perform(post("/api/v1/content")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").value(generatedId.toString()));
    }

    static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}