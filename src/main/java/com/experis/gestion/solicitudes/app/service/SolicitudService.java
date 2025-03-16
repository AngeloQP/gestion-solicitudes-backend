package com.experis.gestion.solicitudes.app.service;

import com.experis.gestion.solicitudes.app.model.dto.SolicitudDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface SolicitudService {
    Mono<SolicitudDTO> saveSolicitud(SolicitudDTO solicitudDTO);
    Flux<SolicitudDTO> getAllSolicitudes();
    Flux<SolicitudDTO> getSolicitudesByFilters(LocalDate startDate, LocalDate endDate, String tipoSolicitud);
    Mono<SolicitudDTO> getSolicitudByCodigo(String codigo);
    Mono<SolicitudDTO> updateSolicitud(Long id, SolicitudDTO solicitudDTO);
    Mono<Void> deleteSolicitud(Long id);
    Mono<ResponseEntity<ByteArrayResource>> exportToCSV();
}
