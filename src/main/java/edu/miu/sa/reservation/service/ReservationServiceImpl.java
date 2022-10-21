package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;
import edu.miu.sa.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    @Value("${kafka.topic.payment}")
    private String paymentTopic;

    private final KafkaService kafkaService;

    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public void save(Reservation reservation) {
        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reservation.setId(UUID.randomUUID());
        reservation.setEmail(email);
        reservation.setTotal(reservation.getPrice() * reservation.getNight());
        reservationRepository.save(reservation);
        kafkaService.publish(paymentTopic,  reservation);
    }

    @Override
    public Reservation getById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find reservation: " + id));
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }
}
