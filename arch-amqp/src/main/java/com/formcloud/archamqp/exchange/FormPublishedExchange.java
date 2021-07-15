package com.formcloud.archamqp.exchange;

import com.formcloud.archamqp.abstracts.EntityBase;
import com.formcloud.archamqp.abstracts.ExchangeBase;
import com.formcloud.archamqp.abstracts.ExchangeBaseBuilder;
import com.formcloud.archamqp.entity.form.FormMSG;

import org.springframework.amqp.core.Queue;

public class FormPublishedExchange  extends ExchangeBase{

    public static final String EXCHANGE_NAME = "amqp.direct.form";
    public static final Class<? extends EntityBase> CLASS_ENTITY = FormMSG.class;

    public static final String QUEUE_NAME_PUBLISHED1_FORM="published1-form";
    public static final String QUEUE_NAME_PUBLISHED2_FORM="published2-form";
    
    public FormPublishedExchange() {
        super( ExchangeBaseBuilder
                .fromFanout(EXCHANGE_NAME, CLASS_ENTITY)
                .addQueue( new Queue(QUEUE_NAME_PUBLISHED1_FORM,false, false, false))
                .addQueue( new Queue(QUEUE_NAME_PUBLISHED1_FORM,false, false, false))
        );
    }
    
}
