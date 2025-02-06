package ru.ipopov.bookingroom.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ipopov.bookingroom.dto.BookingDTO;
import ru.ipopov.bookingroom.entity.Booking;
import ru.ipopov.bookingroom.entity.Room;
import ru.ipopov.bookingroom.exception.ConflictException;
import ru.ipopov.bookingroom.repository.BookingRepository;
import ru.ipopov.bookingroom.repository.RoomRepository;
import ru.ipopov.bookingroom.service.impl.BookingServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void createBookingTest() {
        LocalDateTime startTime = LocalDateTime.parse("2026-02-01T10:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2026-02-01T11:00:00");

        Room room = new Room();
        room.setId(1L);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomService.isRoomAvailable(room,startTime,endTime)).thenReturn(true);
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setRoom(room);

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingDTO result = bookingService.createBooking(1L, startTime, endTime);

        assertNotNull(result);

        assertEquals(1L, result.getId());

        assertEquals(startTime, result.getStartTime());

        assertEquals(endTime, result.getEndTime());

        assertEquals(1L, result.getRoomId());
    }

    @Test
    public void createBookingConflictTest() {
        LocalDateTime startTime = LocalDateTime.parse("2026-02-01T10:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2026-02-01T11:00:00");

        Room room = new Room();
        room.setId(1L);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        assertThrows(ConflictException.class, () -> {
            bookingService.createBooking(1L, startTime, endTime);
        });
    }

    @Test
    public void testDeleteBooking() {
        doNothing().when(bookingRepository).deleteById(1L);

        bookingService.deleteBooking(1L);

        verify(bookingRepository, times(1)).deleteById(1L);
    }
}
