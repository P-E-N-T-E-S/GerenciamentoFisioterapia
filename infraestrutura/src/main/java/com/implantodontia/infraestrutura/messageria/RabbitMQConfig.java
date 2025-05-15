package com.implantodontia.infraestrutura.messageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "notificacao.fila";
    public static final String EXCHANGE = "notificacao.exchange";
    public static final String ROUTING_KEY = "notificacao.chave";

    @Bean
    public Queue fila() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue fila, TopicExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY);
    }
}

