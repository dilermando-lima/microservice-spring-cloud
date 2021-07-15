package com.formcloud.archamqp.exchange;

import com.formcloud.archamqp.abstracts.EntityBase;
import com.formcloud.archamqp.abstracts.ExchangeBase;
import com.formcloud.archamqp.abstracts.ExchangeBaseBuilder;
import com.formcloud.archamqp.abstracts.ExchangeType;
import com.formcloud.archamqp.entity.form.FormMSG;

import org.springframework.amqp.core.Queue;

public class FormUnpublishedExchange  extends ExchangeBase{

    public static final String EXCHANGE_NAME = "";
    public static final Class<? extends EntityBase> CLASS_ENTITY = FormMSG.class;
    public static final ExchangeType EXCHANGE_TYPE = ExchangeType.DIRECT;

    public static final String QUEUE_NAME_UNPUBLISHED_FORM="unpublished-form";

    public FormUnpublishedExchange() {
        super( ExchangeBaseBuilder
                .fromDirect( new Queue(QUEUE_NAME_UNPUBLISHED_FORM,false, false, false), CLASS_ENTITY )
        );
    }
    
}
