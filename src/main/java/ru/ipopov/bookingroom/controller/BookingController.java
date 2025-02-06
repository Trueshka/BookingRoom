package ru.ipopov.bookingroom.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ipopov.bookingroom.dto.BookingDTO;
import ru.ipopov.bookingroom.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Бронирование", description = "Бронирование комнат")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestParam Long roomId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        BookingDTO bookingDTO = bookingService.createBooking(roomId, startTime, endTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBooking(@RequestParam Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
