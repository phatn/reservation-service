package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.domain.NotificationRequest;
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

    @Value("${kafka.topic.property}")
    private String propertyTopic;

    @Value("${kafka.topic.notification}")
    private String notificationTopic;

    @Value("${notification.message.separator}")
    private String separator;

    private final KafkaService kafkaService;

    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public void save(Reservation reservation) {

        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        reservation.setId(UUID.randomUUID());
        reservation.setEmail(email);
        reservation.setTotal(reservation.getPrice() * reservation.getNight());
        log.info("Persisting reservation: {}", reservation);
        reservationRepository.save(reservation);
        kafkaService.publish(paymentTopic, reservation);
        kafkaService.publish(propertyTopic, reservation);
        StringBuilder message = new StringBuilder();
        message.append("Your property reservation details").append(separator);
        message.append("Property ID: " + reservation.getPropertyId()).append(separator);
        message.append("Payment Type: " + reservation.getPaymentType()).append(separator);
        message.append("Price: " + reservation.getPrice()).append(separator);
        message.append("Number of night: " + reservation.getNight()).append(separator);
        message.append("Total: $" + reservation.getTotal()).append(separator);
        NotificationRequest notificationRequest = new NotificationRequest(email, "Reservation Notification",  message.toString());
        kafkaService.publish(notificationTopic, notificationRequest);
        log.info("Reservation was done");
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
