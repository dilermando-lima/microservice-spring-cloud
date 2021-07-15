package com.formcloud.archamqp.template;

import com.formcloud.archamqp.abstracts.RabbitTemplateAbstract;
import com.formcloud.archamqp.config.ConnectionConfig;
import com.formcloud.archamqp.entity.form.FormMSG;
import com.formcloud.archamqp.exchange.FormPublishedExchange;
import com.formcloud.archamqp.exchange.FormUnpublishedExchange;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitTemplateForm extends RabbitTemplateAbstract {

    @Autowired 
    @Qualifier(ConnectionConfig.BEAN_CONN_RABBIT_1)  
    private ConnectionFactory connectionFactory;

    public RabbitTemplateForm() {
        super(connectionFactory);
    }
    
    public void sendPublishedForm(FormMSG formMSG){
        send(new FormPublishedExchange() , formMSG);
    }

    public void sendUnpublishedForm(FormMSG formMSG){
        send(new FormUnpublishedExchange() , formMSG);
    }

}
