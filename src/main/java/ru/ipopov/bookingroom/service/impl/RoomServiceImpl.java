package ru.ipopov.bookingroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ipopov.bookingroom.dto.RoomDTO;
import ru.ipopov.bookingroom.entity.Booking;
import ru.ipopov.bookingroom.entity.Coworking;
import ru.ipopov.bookingroom.entity.Room;
import ru.ipopov.bookingroom.exception.ConflictException;
import ru.ipopov.bookingroom.exception.ResourceNotFoundException;
import ru.ipopov.bookingroom.repository.BookingRepository;
import ru.ipopov.bookingroom.repository.CoworkingRepository;
import ru.ipopov.bookingroom.repository.RoomRepository;
import ru.ipopov.bookingroom.service.RoomService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final CoworkingRepository coworkingRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, BookingRepository bookingRepository, CoworkingRepository coworkingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.coworkingRepository = coworkingRepository;
    }

    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(RoomDTO::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Комната не найдена"));
        return RoomDTO.convertToRoomDTO(room);
    }


    public RoomDTO createRoom(RoomDTO roomDTO) {
        if (roomDTO.getCapacity() < 1 || roomDTO.getCapacity() > 20) {
            throw new ConflictException("Вместимость должна быть от 1 до 20");
        }
        Coworking coworking = coworkingRepository.findById(roomDTO.getCoworkingId())
                .orElseThrow(() -> new ResourceNotFoundException("Коворкинг не найден"));

        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        room.setCoworking(coworking);

        Room savedRoom = roomRepository.save(room);

        return RoomDTO.convertToRoomDTO(savedRoom);
    }

    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        if (roomDTO.getCapacity() < 1 || roomDTO.getCapacity() > 20) {
            throw new ConflictException("Вместимость должна быть от 1 до 20");
        }
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Комната не найдена"));

        if (roomDTO.getName() != null) {
            room.setName(roomDTO.getName());
        }
        if (roomDTO.getCapacity() != null) {
            room.setCapacity(roomDTO.getCapacity());
        }
        if (roomDTO.getCoworkingId() != null) {
            Coworking coworking = coworkingRepository.findById(roomDTO.getCoworkingId())
                    .orElseThrow(() -> new ResourceNotFoundException("Коворкинг не найден"));
            room.setCoworking(coworking);
        }
        Room updatedRoom = roomRepository.save(room);
        return RoomDTO.convertToRoomDTO(updatedRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public List<RoomDTO> findAvailableRoomsByCapacity(int capacity) {
        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqual(capacity);
        return rooms.stream()
                .map(RoomDTO::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> findAvailableRoomsByTime(LocalDateTime startTime, LocalDateTime endTime) {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .filter(room -> isRoomAvailable(room, startTime, endTime))
                .map(RoomDTO::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> findAvailableRooms(int capacity, LocalDateTime startTime, LocalDateTime endTime) {
        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqual(capacity);
        return rooms.stream()
                .filter(room -> isRoomAvailable(room, startTime, endTime))
                .map(RoomDTO::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    public boolean isRoomAvailable(Room room, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> bookings = bookingRepository.findByRoomAndStartTimeLessThanAndEndTimeGreaterThan(room, endTime, startTime);
        return bookings.isEmpty();
    }


}
