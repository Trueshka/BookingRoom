package ru.ipopov.bookingroom.service.impl;

import org.springframework.stereotype.Service;
import ru.ipopov.bookingroom.comon.Utils;
import ru.ipopov.bookingroom.dto.BookingDTO;
import ru.ipopov.bookingroom.dto.CoworkingDTO;
import ru.ipopov.bookingroom.entity.Booking;
import ru.ipopov.bookingroom.entity.Coworking;
import ru.ipopov.bookingroom.entity.Room;
import ru.ipopov.bookingroom.exception.ConflictException;
import ru.ipopov.bookingroom.exception.ResourceNotFoundException;
import ru.ipopov.bookingroom.repository.BookingRepository;
import ru.ipopov.bookingroom.repository.RoomRepository;
import ru.ipopov.bookingroom.service.BookingService;
import ru.ipopov.bookingroom.service.RoomService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookingDTOS = bookingRepository.findAll();
        return bookingDTOS.stream()
                .map(BookingDTO::convertToBookingDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO createBooking(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {

        //Нужно ли проверять время на 0 минут или 0 секунд? Нужно ли проверять что даты должны быть после сегодняшней даты?
//        if (!Utils.isTimeStepValid(startTime) || !Utils.isTimeStepValid(endTime)) {
//            throw new ConflictException("Время бронирования должно быть кратно 30 минутам.");
//        }

        if (!Utils.isDurationStepValid(startTime, endTime)) {
            throw new ConflictException("Длительность бронирования должна быть кратной 30 минутам");
        }
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Комната не найдена"));

        if (!roomService.isRoomAvailable(room, startTime, endTime)) {
            throw new ConflictException("Комната уже занята для данного периода времени");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        Booking savedBooking = bookingRepository.save(booking);
        return BookingDTO.convertToBookingDTO(savedBooking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
