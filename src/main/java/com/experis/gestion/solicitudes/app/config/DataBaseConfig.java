package com.experis.gestion.solicitudes.app.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

@Configuration
public class DataBaseConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    public DataBaseConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate() {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return this.connectionFactory;
    }
}


