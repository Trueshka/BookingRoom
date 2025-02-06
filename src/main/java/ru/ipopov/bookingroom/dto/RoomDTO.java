package ru.ipopov.bookingroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ipopov.bookingroom.entity.Room;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDTO {
    private Long id;
    private String name;
    private Integer capacity;
    private Long coworkingId;
    private List<BookingDTO> bookings;

    public static RoomDTO convertToRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setCapacity(room.getCapacity());
        List<BookingDTO> bookingDTOs = room.getBookings().stream()
                .map(BookingDTO::convertToBookingDTO)
                .collect(Collectors.toList());
        roomDTO.setBookings(bookingDTOs);
        roomDTO.setCoworkingId(room.getCoworking().getId());
        return roomDTO;
    }
}
