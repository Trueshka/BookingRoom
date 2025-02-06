package ru.ipopov.bookingroom.service;

import ru.ipopov.bookingroom.dto.CoworkingDTO;
import ru.ipopov.bookingroom.entity.Coworking;

import java.util.List;

public interface CoworkingService {

    List<CoworkingDTO> getAllCoworkings();

    CoworkingDTO getCoworkingById(Long id);

    CoworkingDTO createCoworking(CoworkingDTO coworkingDTO);

    CoworkingDTO updateCoworking(Long id,CoworkingDTO coworkingDTO);

    void deleteCoworking(Long id);

}
