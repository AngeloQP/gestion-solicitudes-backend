package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.TipoSolicitudMapper;
import com.experis.gestion.solicitudes.app.model.dto.TipoSolicitudDTO;
import com.experis.gestion.solicitudes.app.repository.TipoSolicitudRepository;
import com.experis.gestion.solicitudes.app.service.TipoSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementación del servicio para gestionar los tipos de solicitud.
 */
@Service
public class TipoSolicitudServiceImpl implements TipoSolicitudService {

    private static final Logger logger = LoggerFactory.getLogger(TipoSolicitudServiceImpl.class);

    @Autowired
    private TipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    private TipoSolicitudMapper tipoSolicitudMapper;

    /**
     * Obtiene todos los tipos de solicitud almacenados en la base de datos.
     *
     * @return Flux<TipoSolicitudDTO> con la lista de tipos de solicitud.
     */
    @Override
    public Flux<TipoSolicitudDTO> findAllTipoSolicitud() {
        return tipoSolicitudRepository.findAll()
                .map(tipoSolicitudMapper::toDTO)
                .onErrorResume(e -> {
                    logger.error("Error al obtener los tipos de solicitud: {}", e.getMessage());
                    return Flux.error(new RuntimeException("No se pudieron recuperar los tipos de solicitud."));
                });
    }
}
