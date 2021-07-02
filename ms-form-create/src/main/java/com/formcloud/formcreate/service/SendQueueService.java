package com.formcloud.formcreate.service;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.formcloud.formcreate.domain.dto.FormDTO;
import com.formcloud.springutil.errorhandler.ApiException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendQueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${ms.queue.name.published-form}")
    private String queueNamePublishForm;

    @Value("${ms.queue.name.unpublished-form}")
    private String queueNameUnpublishForm;

    @PostConstruct
    private void configRabbit(){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper().registerModule(new JavaTimeModule()) ));
        rabbitTemplate.setEncoding("UTF-8");
    }
    
    public void sendPublishedFormToQueue(FormDTO formDTO) throws ApiException{
        rabbitTemplate.convertAndSend(queueNamePublishForm, formDTO);
    }

    public void sendUnPublishedFormToQueue(FormDTO formDTO) throws ApiException{
        rabbitTemplate.convertAndSend(queueNameUnpublishForm, formDTO);
    }

}
