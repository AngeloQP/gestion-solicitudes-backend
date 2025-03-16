package com.experis.gestion.solicitudes.app.repository;

import com.experis.gestion.solicitudes.app.model.Marca;
import com.experis.gestion.solicitudes.app.model.dto.MarcaDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MarcaRepository extends ReactiveCrudRepository<Marca, Long> {
    @Query("SELECT * FROM marca ORDER BY descripcion")
    Flux<Marca> findAllMarcas();

    Mono<Marca> findMarcaById(Long marcaID);
}
