package com.formcloud.archamqp.template;

import com.formcloud.archamqp.abstracts.RabbitTemplateAbstract;
import com.formcloud.archamqp.config.ConnectionConfig;
import com.formcloud.archamqp.entity.form.FormMSG;
import com.formcloud.archamqp.exchange.FormPublishedExchange;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitTemplateForm2 extends RabbitTemplateAbstract {

    public RabbitTemplateForm2(@Autowired @Qualifier(ConnectionConfig.BEAN_CONN_RABBIT_2)  ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
    
    public void sendPublishedForm(FormMSG formMSG){
        send(new FormPublishedExchange() , formMSG);
    }

}
