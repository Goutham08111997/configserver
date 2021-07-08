package com.microcredential.deliveryservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "${east.zone.queue.name}")
    public void eastZoneListener(String jsonString) throws JsonProcessingException {
        System.out.println(jsonString);

    }
    @RabbitListener(queues = "${west.zone.queue.name}")
    public void westZoneListener(String jsonString) throws JsonProcessingException {

    }
    @RabbitListener(queues = "${north.zone.queue.name}")
    public void northZoneListener(String jsonString) throws JsonProcessingException {

    }
    @RabbitListener(queues = "${south.zone.queue.name}")
    public void southZoneListener(String jsonString) throws JsonProcessingException {
        System.out.println(jsonString);

    }
}
