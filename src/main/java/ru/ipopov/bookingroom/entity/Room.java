package ru.ipopov.bookingroom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "coworking_id")
    private Coworking coworking;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();

}
