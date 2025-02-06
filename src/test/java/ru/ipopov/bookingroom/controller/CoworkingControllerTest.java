package ru.ipopov.bookingroom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ipopov.bookingroom.dto.CoworkingDTO;
import ru.ipopov.bookingroom.service.CoworkingService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoworkingController.class)
class CoworkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CoworkingService coworkingService;


    @Test
    void getCoworkingByIdTest() throws Exception{
        CoworkingDTO coworkingDTO = new CoworkingDTO();
        coworkingDTO.setId(1L);
        coworkingDTO.setName("Coworking Red");
        coworkingDTO.setLocation("First floor");

        when(coworkingService.getCoworkingById(1L)).thenReturn(coworkingDTO);

        mockMvc.perform(get("/api/coworkings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Coworking Red"))
                .andExpect(jsonPath("$.location").value("First floor"));
    }

    @Test
    void createCoworkingTest() throws Exception{
        CoworkingDTO coworkingDTO = new CoworkingDTO();
        coworkingDTO.setId(1L);
        coworkingDTO.setName("Coworking Red");
        coworkingDTO.setLocation("First floor");

        when(coworkingService.createCoworking(any(CoworkingDTO.class))).thenReturn(coworkingDTO);

        mockMvc.perform(post("/api/coworkings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Coworking Red\",\"location\":\"First floor\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Coworking Red"))
                .andExpect(jsonPath("$.location").value("First floor"));
    }
    }
