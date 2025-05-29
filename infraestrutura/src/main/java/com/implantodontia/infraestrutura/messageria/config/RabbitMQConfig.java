package com.implantodontia.infraestrutura.messageria.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notificacao.exchange";

    public static final String TODAS_NOTIFICACOES_ROUTING_KEY = "notificacao";

    public static final String FILA_NOTIFICACOES = "fila.notificacoes";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue filaTodasNotificacoes() {
        return new Queue(FILA_NOTIFICACOES);
    }

    @Bean
    public Binding bindingTodasNotificacoes() {
        return BindingBuilder
                .bind(filaTodasNotificacoes())
                .to(exchange())
                .with(TODAS_NOTIFICACOES_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}


