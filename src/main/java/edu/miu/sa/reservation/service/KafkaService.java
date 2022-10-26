package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;

public interface KafkaService<T> {

    void publish(String topic, T data);
}
