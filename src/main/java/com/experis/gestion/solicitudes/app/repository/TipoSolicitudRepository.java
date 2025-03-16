package com.experis.gestion.solicitudes.app.repository;

import com.experis.gestion.solicitudes.app.model.TipoSolicitud;
import com.experis.gestion.solicitudes.app.model.dto.TipoSolicitudDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TipoSolicitudRepository extends ReactiveCrudRepository<TipoSolicitud, Long> {
    @Query("SELECT * FROM tipo_solicitud ORDER BY descripcion")
    Flux<TipoSolicitud> findAllTipoSolicitud();

    Mono<TipoSolicitud> findTipoSolicitudById(Long id);
}
