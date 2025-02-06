package ru.ipopov.bookingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ipopov.bookingroom.entity.Booking;
import ru.ipopov.bookingroom.entity.Room;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomAndStartTimeLessThanAndEndTimeGreaterThan(Room room, LocalDateTime startTime, LocalDateTime endTime);
}
