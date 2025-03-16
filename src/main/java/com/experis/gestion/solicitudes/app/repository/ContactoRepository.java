package com.experis.gestion.solicitudes.app.repository;

import com.experis.gestion.solicitudes.app.model.Contacto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ContactoRepository extends ReactiveCrudRepository<Contacto, Long> {
    @Query("SELECT * FROM contactos WHERE solicitud_id = :solicitudId")
    Flux<Contacto> findBySolicitudId(@Param("solicitudId") Long solicitudId);
}
