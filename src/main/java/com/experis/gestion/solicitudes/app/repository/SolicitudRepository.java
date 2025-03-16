package com.experis.gestion.solicitudes.app.repository;

import com.experis.gestion.solicitudes.app.model.Solicitud;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface SolicitudRepository extends ReactiveCrudRepository<Solicitud, Long> {
    Flux<Solicitud> findByFechaEnvioBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT s.* FROM solicitudes s " +
            "JOIN tipo_solicitud ts ON s.tipo_solicitud_id = ts.id " +
            "WHERE ts.descripcion = :tipoSolicitud")
    Flux<Solicitud> findByTipoSolicitudDescripcion(String tipoSolicitud);
    Mono<Solicitud> findByCodigo(String codigo);
    @Query("SELECT codigo FROM solicitudes ORDER BY id DESC LIMIT 1")
    Mono<String> findLastCodigo();

}
