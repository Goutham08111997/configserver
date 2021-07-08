package com.microcredential.packageservice.messaging;

import com.microcredential.packageservice.model.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String EXCHANGE_NAME;

    @Value("${east.zone.routing.key}")
    private String EAST_ZONE_ROUTING_KEY;

    @Value("${west.zone.routing.key}")
    private String WEST_ZONE_ROUTING_KEY;

    @Value("${north.zone.routing.key}")
    private String NORTH_ZONE_ROUTING_KEY;

    @Value("${south.zone.routing.key}")
    private String SOUTH_ZONE_ROUTING_KEY;

    private Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    public MessageProducer(ConnectionFactory connectionFactory) {
        this.rabbitTemplate = new RabbitTemplate(connectionFactory);
    }

    public void sendMessageEastZone(Package newPackage) throws JsonProcessingException {
        String responseValue = new ObjectMapper().writeValueAsString(newPackage);
        LOGGER.info("********** Response for process instance id: " +
                newPackage.getPackageId() +
                " ********** \\n" + responseValue );
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, EAST_ZONE_ROUTING_KEY, responseValue);
    }

    public void sendMessageWestZone(Package newPackage) throws JsonProcessingException {
        String responseValue = new ObjectMapper().writeValueAsString(newPackage);
        LOGGER.info("********** Response for process instance id: " +
                newPackage.getPackageId() +
                " ********** \\n" + responseValue );
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, WEST_ZONE_ROUTING_KEY, responseValue);
    }

    public void sendMessageNorthZone(Package newPackage) throws JsonProcessingException {
        String responseValue = new ObjectMapper().writeValueAsString(newPackage);
        LOGGER.info("********** Response for process instance id: " +
                newPackage.getPackageId() +
                " ********** \\n" + responseValue );

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, NORTH_ZONE_ROUTING_KEY, responseValue);

    }

    public void sendMessageSouthZone(Package newPackage) throws JsonProcessingException {
        System.out.println("South zone");
        String responseValue = new ObjectMapper().writeValueAsString(newPackage);
        LOGGER.info("********** Response for process instance id: " +
                newPackage.getPackageId() +
                " ********** \\n" + responseValue );
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, SOUTH_ZONE_ROUTING_KEY, responseValue);
    }

}
