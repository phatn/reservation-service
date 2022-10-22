package edu.miu.sa.reservation.controller;

import edu.miu.sa.reservation.domain.Response;
import edu.miu.sa.reservation.entity.Reservation;
import edu.miu.sa.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@Slf4j
@CrossOrigin
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Response reserve(@RequestBody Reservation reservation) {
        reservationService.save(reservation);
        return new Response("Saved reservation");
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable UUID id) {
        return reservationService.getById(id);
    }
}
