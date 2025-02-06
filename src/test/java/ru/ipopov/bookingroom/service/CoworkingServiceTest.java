package ru.ipopov.bookingroom.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ipopov.bookingroom.dto.CoworkingDTO;
import ru.ipopov.bookingroom.entity.Coworking;
import ru.ipopov.bookingroom.exception.ResourceNotFoundException;
import ru.ipopov.bookingroom.repository.CoworkingRepository;
import ru.ipopov.bookingroom.service.impl.CoworkingServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoworkingServiceTest {
    @Mock
    private CoworkingRepository coworkingRepository;

    @InjectMocks
    private CoworkingServiceImpl coworkingService;

    @Test
    public void createCoworkingTest() {
        CoworkingDTO  coworkingDTO= new CoworkingDTO();
        coworkingDTO.setName("Coworking Red");
        coworkingDTO.setLocation("First floor");

        Coworking coworking = new Coworking();
        coworking.setName(coworkingDTO.getName());
        coworking.setLocation(coworkingDTO.getLocation());

        when(coworkingRepository.save(any(Coworking.class))).thenReturn(coworking);

        CoworkingDTO result = coworkingService.createCoworking(coworkingDTO);

        assertNotNull(result);
        assertEquals("Coworking Red", result.getName());
        assertEquals("First floor", result.getLocation());
    }

    @Test
    public void getCoworkingByIdTest() {
        Coworking coworking = new Coworking();
        coworking.setId(1L);
        coworking.setName("Coworking Red");
        coworking.setLocation("First floor");

        when(coworkingRepository.findById(1L)).thenReturn(Optional.of(coworking));

        CoworkingDTO result = coworkingService.getCoworkingById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Coworking Red", result.getName());
        assertEquals("First floor", result.getLocation());
    }

    @Test
    public void getCoworkingByIdNotFoundTest() {
        when(coworkingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            coworkingService.getCoworkingById(1L);
        });
    }

    @Test
    public void updateCoworkingTest() {
        CoworkingDTO coworkingDTO = new CoworkingDTO();
        coworkingDTO.setName("Coworking Blue");
        coworkingDTO.setLocation("Second floor");

        Coworking coworking = new Coworking();
        coworking.setId(1L);
        coworking.setName("Coworking Red");
        coworking.setLocation("First floor");

        when(coworkingRepository.findById(1L)).thenReturn(Optional.of(coworking));
        when(coworkingRepository.save(any(Coworking.class))).thenReturn(coworking);

        CoworkingDTO result = coworkingService.updateCoworking(1L, coworkingDTO);

        assertNotNull(result);
        assertEquals("Coworking Blue", result.getName());
        assertEquals("Second floor", result.getLocation());
    }
}