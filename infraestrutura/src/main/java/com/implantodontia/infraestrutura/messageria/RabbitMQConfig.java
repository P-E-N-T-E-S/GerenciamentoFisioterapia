package com.implantodontia.infraestrutura.messageria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notificacao.exchange";

    public static final String NOVOS_CLIENTES_ROUTING_KEY = "notificacao.novos-clientes";
    public static final String RELEMBRETE_ROUTING_KEY = "notificacao.relembrar-agendamentos";
    public static final String PAGAMENTOS_ROUTING_KEY = "notificacao.pagamentos";
    public static final String TODAS_NOTIFICACOES_ROUTING_KEY = "notificacao.#";

    public static final String FILA_NOVOS_CLIENTES = "fila.novos-clientes";
    public static final String FILA_RELEMBRETE = "fila.relembrar-agendamentos";
    public static final String FILA_PAGAMENTO = "fila.pagamento";
    public static final String FILA_TODAS_NOTIFICACOES = "fila.notificacoes";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue filaNovosClientes() {
        return new Queue(FILA_NOVOS_CLIENTES);
    }

    @Bean
    public Queue filaRelembrarAgendamentos() {
        return new Queue(FILA_RELEMBRETE);
    }

    @Bean
    public Queue filaPagamento() {
        return new Queue(FILA_PAGAMENTO);
    }

    @Bean
    public Queue filaTodasNotificacoes() {
        return new Queue(FILA_TODAS_NOTIFICACOES);
    }

    @Bean
    public Binding bindingNovosClientes() {
        return BindingBuilder
                .bind(filaNovosClientes())
                .to(exchange())
                .with(NOVOS_CLIENTES_ROUTING_KEY);
    }

    @Bean
    public Binding bindingRelembrar() {
        return BindingBuilder
                .bind(filaRelembrarAgendamentos())
                .to(exchange())
                .with(RELEMBRETE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingPagamento() {
        return BindingBuilder
                .bind(filaPagamento())
                .to(exchange())
                .with(PAGAMENTOS_ROUTING_KEY);
    }

    @Bean
    public Binding bindingTodasNotificacoes() {
        return BindingBuilder
                .bind(filaTodasNotificacoes())
                .to(exchange())
                .with(TODAS_NOTIFICACOES_ROUTING_KEY);
    }
}


