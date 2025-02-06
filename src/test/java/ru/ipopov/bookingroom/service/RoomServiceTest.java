package ru.ipopov.bookingroom.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ipopov.bookingroom.dto.RoomDTO;
import ru.ipopov.bookingroom.entity.Coworking;
import ru.ipopov.bookingroom.entity.Room;
import ru.ipopov.bookingroom.exception.ResourceNotFoundException;
import ru.ipopov.bookingroom.repository.CoworkingRepository;
import ru.ipopov.bookingroom.repository.RoomRepository;
import ru.ipopov.bookingroom.service.impl.RoomServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @Mock
    private CoworkingRepository coworkingRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    public void createRoomTest() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("Room 1");
        roomDTO.setCapacity(10);
        roomDTO.setCoworkingId(1L);

        Coworking coworking = new Coworking();
        coworking.setId(1L);

        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setCoworking(coworking);

        when(coworkingRepository.findById(1L)).thenReturn(Optional.of(coworking));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDTO result = roomService.createRoom(roomDTO);

        assertNotNull(result);
        assertEquals("Room 1", result.getName());
        assertEquals(10, result.getCapacity());
        assertEquals(1L, result.getCoworkingId());
    }

    @Test
    public void getRoomByIdTest() {
        Room room = new Room();
        room.setId(1L);
        room.setName("Room 1");
        room.setCapacity(10);

        Coworking coworking = new Coworking();
        coworking.setId(1L);
        room.setCoworking(coworking);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.getRoomById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Room 1", result.getName());
        assertEquals(10, result.getCapacity());
        assertEquals(1L, result.getCoworkingId());
    }

    @Test
    public void getRoomByIdNotFoundTest() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            roomService.getRoomById(1L);
        });
    }

    @Test
    public void updateRoomTest() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("Updated Room 1");
        roomDTO.setCapacity(12);

        Coworking coworking = new Coworking();
        coworking.setId(1L);

        Room room = new Room();
        room.setId(1L);
        room.setName("Room 1");
        room.setCapacity(10);
        room.setCoworking(coworking);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomDTO result = roomService.updateRoom(1L, roomDTO);

        assertNotNull(result);
        assertEquals("Updated Room 1", result.getName());
        assertEquals(12, result.getCapacity());
    }
}