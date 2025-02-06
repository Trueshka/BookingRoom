package ru.ipopov.bookingroom.service;

import ru.ipopov.bookingroom.dto.BookingDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    List<BookingDTO> getAllBookings();

    BookingDTO createBooking(Long roomId, LocalDateTime startTime, LocalDateTime endTime);

    void deleteBooking(Long id);
}
