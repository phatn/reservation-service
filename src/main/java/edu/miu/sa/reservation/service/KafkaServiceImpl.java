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
public class KafkaServiceImpl<T> implements KafkaService<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void publish(String topic, T data) {
        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(topic, data);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(final SendResult<String, T> message) {
                log.info("sent message= " + message + " with offset= " + message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                log.error("unable to send message= " + data, throwable);
            }
        });

    }
}

