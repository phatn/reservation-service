package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    void save(Reservation reservation);

    Reservation getById(UUID id);

    List<Reservation> getAll();
}
