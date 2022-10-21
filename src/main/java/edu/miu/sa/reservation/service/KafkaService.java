package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;

public interface KafkaService {

    void publish(String topic, Reservation reservation);

    void listen(Reservation reservation);
}
