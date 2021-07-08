package com.microcredential.deliveryservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

@Configuration
public class MessageConfiguration {
    @Value("${east.zone.queue.name}")
    private String EAST_ZONE_QUEUE;

    @Value("${west.zone.queue.name}")
    private String WEST_ZONE_QUEUE;

    @Value("${north.zone.queue.name}")
    private String NORTH_ZONE_QUEUE;

    @Value("${south.zone.queue.name}")
    private String SOUTH_ZONE_QUEUE;

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


    @Bean
    public Queue eastZoneQueue(){
        return new Queue(EAST_ZONE_QUEUE);
    }

    @Bean
    public Queue westZoneQueue(){ return new Queue(WEST_ZONE_QUEUE); }

    @Bean
    public Queue northZoneQueue(){
        return new Queue(NORTH_ZONE_QUEUE);
    }

    @Bean
    public Queue southZoneQueue(){
        return new Queue(SOUTH_ZONE_QUEUE);
    }

    @Bean
    public Exchange exchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding eastZoneQueueBinding(Queue eastZoneQueue, DirectExchange directExchange){
        return BindingBuilder.bind(eastZoneQueue).to(directExchange).with(EAST_ZONE_ROUTING_KEY);
    }
    @Bean
    public Binding westZoneQueueBinding(Queue westZoneQueue, DirectExchange directExchange){
        return BindingBuilder.bind(westZoneQueue).to(directExchange).with(WEST_ZONE_ROUTING_KEY);
    }
    @Bean
    public Binding northZoneQueueBinding(Queue northZoneQueue, DirectExchange directExchange){
        return BindingBuilder.bind(northZoneQueue).to(directExchange).with(NORTH_ZONE_ROUTING_KEY);
    }
    @Bean
    public Binding southZoneQueueBinding(Queue southZoneQueue, DirectExchange directExchange){
        return BindingBuilder.bind(southZoneQueue).to(directExchange).with(SOUTH_ZONE_ROUTING_KEY);
    }


}
