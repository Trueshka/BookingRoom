package ru.ipopov.bookingroom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ipopov.bookingroom.dto.RoomDTO;
import ru.ipopov.bookingroom.service.RoomService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RoomController.class)
class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;


    @Test
    public void getRoomByIdTest() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Room 1");
        roomDTO.setCapacity(10);
        roomDTO.setCoworkingId(1L);

        when(roomService.getRoomById(1L)).thenReturn(roomDTO);

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Room 1"))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.coworkingId").value(1L));
    }

    @Test
    public void createRoomTest() throws Exception {

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Room 1");
        roomDTO.setCapacity(10);
        roomDTO.setCoworkingId(1L);

        when(roomService.createRoom(any(RoomDTO.class))).thenReturn(roomDTO);

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Room 1\",\"capacity\":10,\"coworkingId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Room 1"))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.coworkingId").value(1L));
    }

    @Test
    public void updateRoomTest() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Updated Room 1");
        roomDTO.setCapacity(12);
        roomDTO.setCoworkingId(1L);

        when(roomService.updateRoom(eq(1L), any(RoomDTO.class))).thenReturn(roomDTO);

        mockMvc.perform(put("/api/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Room 1\",\"capacity\":12}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Room 1"))
                .andExpect(jsonPath("$.capacity").value(12));
    }

    @Test
    public void deleteRoomTest() throws Exception {
        doNothing().when(roomService).deleteRoom(1L);

        mockMvc.perform(delete("/api/rooms/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindAvailableRooms() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Room 1");
        roomDTO.setCapacity(10);
        roomDTO.setCoworkingId(1L);

        when(roomService.findAvailableRooms(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(roomDTO));

        mockMvc.perform(get("/api/rooms/available")
                        .param("capacity", "10")
                        .param("startTime", "2025-02-01T10:00:00")
                        .param("endTime", "2025-02-01T11:30:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Room 1"))
                .andExpect(jsonPath("$[0].capacity").value(10))
                .andExpect(jsonPath("$[0].coworkingId").value(1L));
    }
}