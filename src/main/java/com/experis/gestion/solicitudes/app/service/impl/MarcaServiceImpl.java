package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.MarcaMapper;
import com.experis.gestion.solicitudes.app.model.dto.MarcaDTO;
import com.experis.gestion.solicitudes.app.repository.MarcaRepository;
import com.experis.gestion.solicitudes.app.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementación del servicio {@link MarcaService}.
 * Proporciona la lógica de negocio para la gestión de marcas.
 */
@Service
public class MarcaServiceImpl implements MarcaService {

    private static final Logger logger = LoggerFactory.getLogger(MarcaServiceImpl.class);

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaMapper marcaMapper;

    /**
     * Recupera todas las marcas almacenadas en la base de datos y las convierte en objetos {@link MarcaDTO}.
     *
     * @return Un {@link Flux} que emite la lista de marcas en formato {@link MarcaDTO}.
     */
    @Override
    public Flux<MarcaDTO> findAllMarcas() {
        return marcaRepository.findAllMarcas()
                .map(marcaMapper::toDTO)
                .onErrorResume(e -> {
                    logger.error("Error al obtener todas las marcas: {}", e.getMessage());
                    return Flux.error(new RuntimeException("No se pudieron recuperar las marcas."));
                });
    }
}
