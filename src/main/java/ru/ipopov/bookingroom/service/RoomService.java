package ru.ipopov.bookingroom.service;

import ru.ipopov.bookingroom.dto.RoomDTO;
import ru.ipopov.bookingroom.entity.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {

    List<RoomDTO> getAllRooms();

    RoomDTO getRoomById(Long id);

    RoomDTO createRoom(RoomDTO roomDTO);

    RoomDTO updateRoom(Long id, RoomDTO roomDTO);

    void deleteRoom(Long id);

    List<RoomDTO> findAvailableRoomsByCapacity(int capacity);

    List<RoomDTO> findAvailableRoomsByTime(LocalDateTime startTime, LocalDateTime endTime);

    List<RoomDTO> findAvailableRooms(int capacity, LocalDateTime startTime, LocalDateTime endTime);

    boolean isRoomAvailable(Room room, LocalDateTime startTime, LocalDateTime endTime);

}
