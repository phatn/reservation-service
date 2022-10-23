package edu.miu.sa.reservation.service;

import edu.miu.sa.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, Reservation> kafkaTemplate;

    @Override
    public void publish(String topic, Reservation reservation) {
        ListenableFuture<SendResult<String, Reservation>> future = kafkaTemplate.send(topic, reservation);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(final SendResult<String, Reservation> message) {
                log.info("sent message= " + message + " with offset= " + message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.error("unable to send message= " + reservation, throwable);
            }
        });

    }
}

