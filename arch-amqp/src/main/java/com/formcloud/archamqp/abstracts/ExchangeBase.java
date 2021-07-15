package com.formcloud.archamqp.abstracts;

import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Queue;

public class ExchangeBase extends AbstractExchange {
    

    private final Class<? extends EntityBase> classEntity;
    private final ExchangeType exchangeType;
    private final List<Queue> queueList;

    public ExchangeBase(ExchangeBaseBuilder exchangeBaseBuilder ){
        this(exchangeBaseBuilder.getName(), 
            exchangeBaseBuilder.getType(),
            exchangeBaseBuilder.getClassEntity(), 
            exchangeBaseBuilder.isDurable(), 
            exchangeBaseBuilder.isAutoDelete(), 
            exchangeBaseBuilder.getArgs(),
            exchangeBaseBuilder.getQueueList());
    }

    public ExchangeBase(String name, ExchangeType type,Class<? extends EntityBase> classEntity, boolean durable, boolean autoDelete, Map<String, Object> arguments, List<Queue> queueList){ 
        super(name == null ? "" : name, durable, autoDelete, arguments);
        this.queueList = queueList;
        this.classEntity = classEntity;
        this.exchangeType = type;
    }

    public ExchangeBase addQueue(Queue queue){
        queueList.add(queue);
        return this;
    }

    public List<Queue> getQueueList() {
        return queueList;
    }

    @Override
    public String getType() {
        return exchangeType.getType();
    }

    public Class<? extends EntityBase> getClassEntity() {
        return classEntity;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    
}
