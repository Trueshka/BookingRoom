package ru.ipopov.bookingroom.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ipopov.bookingroom.dto.CoworkingDTO;
import ru.ipopov.bookingroom.service.CoworkingService;

import java.util.List;

@RestController
@RequestMapping("/api/coworkings")
@Tag(name = "Коворкинг", description = "crud для коворкингов")
public class CoworkingController {

    private final CoworkingService coworkingService;

    public CoworkingController(CoworkingService coworkingService) {
        this.coworkingService = coworkingService;
    }

    @GetMapping
    public ResponseEntity<List<CoworkingDTO>> getAllCoworkings() {
        List<CoworkingDTO> coworkings = coworkingService.getAllCoworkings();
        return ResponseEntity.ok(coworkings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoworkingDTO> getCoworkingById(@PathVariable Long id) {
        CoworkingDTO coworking = coworkingService.getCoworkingById(id);
        return ResponseEntity.ok(coworking);
    }

    @PostMapping
    public ResponseEntity<CoworkingDTO> createCoworking(@RequestBody CoworkingDTO coworkingDTO) {
        CoworkingDTO createdCoworking = coworkingService.createCoworking(coworkingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoworking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoworkingDTO> updateCoworking(@PathVariable Long id, @RequestBody CoworkingDTO coworkingDTO) {
        CoworkingDTO updatedCoworking = coworkingService.updateCoworking(id, coworkingDTO);
        return ResponseEntity.ok(updatedCoworking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoworkingById(@PathVariable Long id) {
        coworkingService.deleteCoworking(id);
        return ResponseEntity.noContent().build();
    }
}
