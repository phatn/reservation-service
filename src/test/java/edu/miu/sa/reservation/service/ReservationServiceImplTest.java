package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;
import edu.miu.sa.reservation.repository.ReservationRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private KafkaService kafkaService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Test
    void should_save_reservation() {
        String email = "phatnguyen@outlook.com";
        lenient().when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(null, null, null));
        lenient().when(authentication.getPrincipal()).thenReturn(email);
        SecurityContextHolder.setContext(securityContext);

        UUID propertyId = UUID.randomUUID();
        Reservation reservation = new Reservation(null, null, propertyId, "Credit", 200, 3, 0);
        reservationService.save(reservation);
        assertThat(reservation.getTotal()).isEqualTo(600);
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getPropertyId()).isEqualTo(propertyId);

    }

    @Test
    void should_return_reservation() {
        UUID reservationId = UUID.randomUUID();
        UUID propertyId = UUID.randomUUID();
        Reservation expectedReservation = new Reservation(reservationId, "phatnguyen@outlook.com",propertyId, "Credit", 200, 3, 600);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(expectedReservation));
        assertThat(reservationService.getById(reservationId)).isEqualTo(expectedReservation);

    }

    @Test
    void should_throw_exception() {
        UUID reservationId = UUID.randomUUID();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> reservationService.getById(reservationId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cannot find reservation: " + reservationId);

    }
}