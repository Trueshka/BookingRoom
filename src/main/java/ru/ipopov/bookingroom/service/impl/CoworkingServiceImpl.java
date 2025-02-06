package ru.ipopov.bookingroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ipopov.bookingroom.dto.CoworkingDTO;
import ru.ipopov.bookingroom.entity.Coworking;
import ru.ipopov.bookingroom.exception.ResourceNotFoundException;
import ru.ipopov.bookingroom.repository.CoworkingRepository;
import ru.ipopov.bookingroom.service.CoworkingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoworkingServiceImpl implements CoworkingService {

    private final CoworkingRepository coworkingRepository;

    @Autowired
    public CoworkingServiceImpl(CoworkingRepository coworkingRepository) {
        this.coworkingRepository = coworkingRepository;
    }

    public List<CoworkingDTO> getAllCoworkings() {
        List<Coworking> coworkings = coworkingRepository.findAll();
        return coworkings.stream()
                .map(CoworkingDTO::convertToCoworkingDTO)
                .collect(Collectors.toList());
    }

    public CoworkingDTO getCoworkingById(Long id) {
        Coworking coworking = coworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Коворкинг не найден"));
        return CoworkingDTO.convertToCoworkingDTO(coworking);
    }

    public CoworkingDTO createCoworking(CoworkingDTO coworkingDTO) {
        Coworking coworking = new Coworking();
        coworking.setName(coworkingDTO.getName());
        coworking.setLocation(coworkingDTO.getLocation());

        Coworking savedCoworking = coworkingRepository.save(coworking);

        return CoworkingDTO.convertToCoworkingDTO(savedCoworking);
    }

    public CoworkingDTO updateCoworking(Long id, CoworkingDTO coworkingDTO) {
        Coworking coworking = coworkingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Коворкинг не найден"));

        if (coworkingDTO.getName() != null) {
            coworking.setName(coworkingDTO.getName());
        }
        if (coworkingDTO.getLocation() != null) {
            coworking.setLocation(coworkingDTO.getLocation());
        }

        Coworking updatedCoworking = coworkingRepository.save(coworking);

        return CoworkingDTO.convertToCoworkingDTO(updatedCoworking);
    }

    public void deleteCoworking(Long id) {
        coworkingRepository.deleteById(id);
    }

}
