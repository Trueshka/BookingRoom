package ru.ipopov.bookingroom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ipopov.bookingroom.dto.BookingDTO;
import ru.ipopov.bookingroom.exception.ConflictException;
import ru.ipopov.bookingroom.service.BookingService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;


    @Test
    public void createBookingTest() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(1L);
        bookingDTO.setStartTime(LocalDateTime.parse("2025-02-01T10:00:00"));
        bookingDTO.setEndTime(LocalDateTime.parse("2025-02-01T11:30:00"));
        bookingDTO.setRoomId(1L);

        when(bookingService.createBooking(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(bookingDTO);

        mockMvc.perform(post("/api/bookings")
                        .param("roomId", "1")
                        .param("startTime", "2025-02-01T10:00:00")
                        .param("endTime", "2025-02-01T11:30:00"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.startTime").value("2025-02-01T10:00:00"))
                .andExpect(jsonPath("$.endTime").value("2025-02-01T11:30:00"))
                .andExpect(jsonPath("$.roomId").value(1L));
    }

    @Test
    public void createBookingConflictTest() throws Exception {
        when(bookingService.createBooking(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenThrow(new ConflictException("Комната уже занята для данного периода времени"));

        mockMvc.perform(post("/api/bookings")
                        .param("roomId", "1")
                        .param("startTime", "2025-02-01T10:00:00")
                        .param("endTime", "2025-02-01T11:30:00"))
                .andExpect(status().isConflict());
    }

}