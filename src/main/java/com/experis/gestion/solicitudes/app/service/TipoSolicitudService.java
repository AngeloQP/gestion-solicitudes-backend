package com.experis.gestion.solicitudes.app.service;

import com.experis.gestion.solicitudes.app.model.dto.TipoSolicitudDTO;
import reactor.core.publisher.Flux;

public interface TipoSolicitudService {
    Flux<TipoSolicitudDTO> findAllTipoSolicitud();
}
