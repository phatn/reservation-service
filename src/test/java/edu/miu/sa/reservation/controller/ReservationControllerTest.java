package edu.miu.sa.reservation.controller;

import edu.miu.sa.reservation.entity.Reservation;
import edu.miu.sa.reservation.security.JwtFilter;
import edu.miu.sa.reservation.security.JwtHelper;
import edu.miu.sa.reservation.service.ReservationService;
import edu.miu.sa.reservation.utils.TestUtils;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtHelper jwtHelper;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private ReservationService reservationService;

    @Test
    public void should_reserve_property() throws Exception {
        UUID reservationId = UUID.randomUUID();
        UUID propertyId = UUID.randomUUID();
        Reservation reservation = new Reservation(reservationId, "phatnguyen@outlook.com", propertyId, "Credit", 200, 3, 600);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer jwt");
        mockMvc.perform(post("/api/reservations")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.stringify(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Saved reservation")));
    }

    @Test
    public void should_return_reservation() throws Exception {
        UUID reservationId = UUID.randomUUID();
        UUID propertyId = UUID.randomUUID();
        Reservation reservation = new Reservation(reservationId, "phatnguyen@outlook.com", propertyId, "Credit", 200, 3, 600);
        Mockito.when(reservationService.getById(reservationId)).thenReturn(reservation);
        mockMvc.perform(get("/api/reservations/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(reservation.getId().toString())))
                .andExpect(jsonPath("$.email", is(reservation.getEmail())))
                .andExpect(jsonPath("$.propertyId", is(reservation.getPropertyId().toString())))
                .andExpect(jsonPath("$.paymentType", is(reservation.getPaymentType())))
                .andExpect(jsonPath("$.price", is(reservation.getPrice())))
                .andExpect(jsonPath("$.night", is(reservation.getNight())))
                .andExpect(jsonPath("$.total", is(reservation.getTotal())));


    }


}