package com.experis.gestion.solicitudes.app.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer {

    private final DatabaseClient client;

    public DataBaseInitializer(ConnectionFactory connectionFactory) {
        this.client = DatabaseClient.create(connectionFactory);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        client.sql("INSERT INTO tipo_solicitud (descripcion) VALUES ('Reparacion')")
                .then()
                .then(client.sql("INSERT INTO tipo_solicitud (descripcion) VALUES ('Mantenimiento')").then())
                .then(client.sql("INSERT INTO tipo_solicitud (descripcion) VALUES ('Inspeccion')").then())

                // Inserts para 'marca'
                .then(client.sql("INSERT INTO marca (descripcion) VALUES ('Toyota')").then())
                .then(client.sql("INSERT INTO marca (descripcion) VALUES ('Honda')").then())
                .then(client.sql("INSERT INTO marca (descripcion) VALUES ('Ford')").then())

                // Inserts para 'solicitudes'
                .then(client.sql("INSERT INTO solicitudes (codigo, marca_id, tipo_solicitud_id, nombre_contacto, numero_contacto) " +
                        "VALUES ('SOL001', 1, 1, 'Juan Perez', '902772911')").then())
                .then(client.sql("INSERT INTO solicitudes (codigo, marca_id, tipo_solicitud_id, nombre_contacto, numero_contacto) " +
                        "VALUES ('SOL002', 2, 2, 'María Lopez', '987456123')").then())
                .then(client.sql("INSERT INTO solicitudes (codigo, marca_id, tipo_solicitud_id, nombre_contacto, numero_contacto) " +
                        "VALUES ('SOL003', 3, 3, 'Carlos Gomez', '922767981')").then())

                // Inserts para 'contactos'
                .then(client.sql("INSERT INTO contactos (solicitud_id, nombre_contacto, numero_contacto) " +
                        "VALUES ((SELECT id FROM solicitudes WHERE codigo = 'SOL001' LIMIT 1), 'Angelo Querevalu', '902772911')").then())
                .then(client.sql("INSERT INTO contactos (solicitud_id, nombre_contacto, numero_contacto) " +
                        "VALUES ((SELECT id FROM solicitudes WHERE codigo = 'SOL002' LIMIT 1), 'Jose Gomez', '987456123')").then())
                .then(client.sql("INSERT INTO contactos (solicitud_id, nombre_contacto, numero_contacto) " +
                        "VALUES ((SELECT id FROM solicitudes WHERE codigo = 'SOL003' LIMIT 1), 'Carlos Perez', '922767981')").then())

                .subscribe();
    }
}
