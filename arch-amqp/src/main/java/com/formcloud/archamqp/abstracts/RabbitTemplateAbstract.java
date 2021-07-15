package com.formcloud.archamqp.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;



public abstract class RabbitTemplateAbstract {

    private static final String ENCODING_DEFAULT = "UTF-8";
    private RabbitTemplate rabbitTemplate;
    private RabbitAdmin rabbitAdmin;

    public RabbitTemplateAbstract(ConnectionFactory connectionFactory ){
        this.rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper().registerModule(new JavaTimeModule()) ));
        rabbitTemplate.setEncoding(ENCODING_DEFAULT);
    }

    public void setMessageConverter(MessageConverter messageConverter){
        rabbitTemplate.setMessageConverter(messageConverter);
    }

    public void setEncoding(String encoding){
        rabbitTemplate.setEncoding(encoding);
    }

    protected void send(ExchangeBase exchangeBase, EntityBase body ) throws RuntimeException {

        if( exchangeBase == null || body == null )
            throw new RuntimeException("exchangeBase and  body cannot be null");

        rabbitTemplate.convertSendAndReceive(
            exchangeBase.getName(), 
            exchangeBase.getExchangeType() == ExchangeType.FANOUT ? "" : exchangeBase.getQueueList().get(0).getName()  , 
            body);
 
    }
       
    public RabbitTemplateAbstract createBinding(ExchangeBase exchangeBase){ 

        exchangeBase.getQueueList().forEach(rabbitAdmin::declareQueue);

        if( exchangeBase.getExchangeType() != ExchangeType.DIRECT && !exchangeBase.getName().isEmpty()   )
            rabbitAdmin.declareExchange(exchangeBase);

        exchangeBase.getQueueList().forEach( q -> {
            rabbitAdmin.declareBinding( new Binding(q.getName(), 
                                                exchangeBase.getExchangeType() == ExchangeType.FANOUT ? Binding.DestinationType.EXCHANGE : Binding.DestinationType.QUEUE, 
                                                exchangeBase.getName(), 
                                                exchangeBase.getExchangeType() == ExchangeType.FANOUT ? "" : q.getName(),
                                                null));
        });

        return this;
    }


}
