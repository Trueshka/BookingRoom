package ru.ipopov.bookingroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ipopov.bookingroom.entity.Coworking;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoworkingDTO {

    private Long id;

    private String name;
    private String location;
    private List<RoomDTO> rooms;

    public static CoworkingDTO convertToCoworkingDTO(Coworking coworking) {
        CoworkingDTO coworkingDTO = new CoworkingDTO();
        coworkingDTO.setId(coworking.getId());
        coworkingDTO.setName(coworking.getName());
        coworkingDTO.setLocation(coworking.getLocation());

        List<RoomDTO> roomDTOs = coworking.getRooms().stream()
                .map(RoomDTO::convertToRoomDTO)
                .collect(Collectors.toList());
        coworkingDTO.setRooms(roomDTOs);

        return coworkingDTO;
    }
}
