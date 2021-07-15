package com.formcloud.archamqp.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfig {

    public static final String BEAN_CONN_RABBIT_1 = "BEAN_CONN_RABBIT_1";
    public static final String BEAN_CONN_RABBIT_2 = "BEAN_CONN_RABBIT_2";

    @Bean(BEAN_CONN_RABBIT_1)
    public ConnectionFactory connectionFactory1() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("host");
        connectionFactory.setPort(0);
        connectionFactory.setUsername("username");
        connectionFactory.setPassword("password");
        connectionFactory.setVirtualHost("virtualHost");
        return connectionFactory;
    }

    @Bean(BEAN_CONN_RABBIT_2)
    public ConnectionFactory connectionFactory2() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("host");
        connectionFactory.setPort(0);
        connectionFactory.setUsername("username");
        connectionFactory.setPassword("password");
        connectionFactory.setVirtualHost("virtualHost");
        return connectionFactory;
    }
    
}
