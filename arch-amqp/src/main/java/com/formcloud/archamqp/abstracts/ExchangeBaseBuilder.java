package com.formcloud.archamqp.abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.Queue;



public class ExchangeBaseBuilder {

    private final String name;
    private final ExchangeType type;
    private final Class<? extends EntityBase> classEntity;
    private boolean durable;
	private boolean autoDelete;
    private Map<String, Object> args;
    private List<Queue> queueList;
    

    public ExchangeBaseBuilder(String name, ExchangeType type, Class<? extends EntityBase> classEntity, boolean durable,  boolean autoDelete, Map<String, Object> args) {
        this.name = name;
        this.type = type;
        this.classEntity = classEntity;
        this.durable = durable;
        this.autoDelete = autoDelete;
        this.args = args;
        this.queueList = new ArrayList<>();
    }

    public static ExchangeBaseBuilder fromDirect(String exchangeName, Queue queue, Class<? extends EntityBase> classEntity){
        return new ExchangeBaseBuilder(exchangeName, ExchangeType.DIRECT, classEntity, false, false, null).addQueue(queue);
    }

    public static ExchangeBaseBuilder fromDirect(Queue queue, Class<? extends EntityBase> classEntity){
        return new ExchangeBaseBuilder("", ExchangeType.DIRECT, classEntity, false, false, null).addQueue(queue);
    }

    public static ExchangeBaseBuilder fromFanout(String name, Class<? extends EntityBase> classEntity){
        return new ExchangeBaseBuilder("", ExchangeType.FANOUT, classEntity, false, false, null);
    }

    public boolean isDurable() {
        return durable;
    }

    public ExchangeBaseBuilder durable() {
        this.durable = true;
        return this;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public List<Queue> getQueueList() {
        return queueList;
    }

    public ExchangeBaseBuilder addQueue(Queue queue) {
        this.queueList.add(queue);
        return this;
    }

    public ExchangeBaseBuilder autoDelete() {
        this.autoDelete = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExchangeType getType() {
        return type;
    }

    public Class<? extends EntityBase> getClassEntity() {
        return classEntity;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public ExchangeBaseBuilder args(Map<String, Object> args) {
        this.args = args;
        return this;
    }

    
}
