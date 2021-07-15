package com.formcloud.formcreate.config;

import com.formcloud.archamqp.exchange.FormPublishedExchange;
import com.formcloud.archamqp.exchange.FormUnpublishedExchange;
import com.formcloud.archamqp.template.RabbitTemplateForm;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public RabbitTemplateForm rabbRabbitForm(){
        return (RabbitTemplateForm) new RabbitTemplateForm(rabbitTemplate)
                                        .createBinding(new FormPublishedExchange() )
                                        .createBinding(new FormUnpublishedExchange());
    }

    
}
