package ru.ipopov.bookingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ipopov.bookingroom.entity.Coworking;

@Repository
public interface CoworkingRepository extends JpaRepository<Coworking,Long> {
}
