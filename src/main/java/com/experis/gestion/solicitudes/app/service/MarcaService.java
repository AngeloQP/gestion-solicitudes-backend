package com.experis.gestion.solicitudes.app.service;

import com.experis.gestion.solicitudes.app.model.dto.MarcaDTO;
import reactor.core.publisher.Flux;

public interface MarcaService {
    Flux<MarcaDTO> findAllMarcas();
}
