package com.experis.gestion.solicitudes.app.controller;

import com.experis.gestion.solicitudes.app.model.dto.TipoSolicitudDTO;
import com.experis.gestion.solicitudes.app.service.TipoSolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/tipoSolicitud")
@Tag(name = "Tipo de Solicitud", description = "API para la gestión de los tipos de solicitud")
public class TipoSolicitudController {

    @Autowired
    private TipoSolicitudService tipoSolicitudService;

    @Operation(summary = "Obtener todos los tipos de solicitud", description = "Devuelve una lista con todos los tipos de solicitud disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de tipos de solicitud obtenida con éxito")
    @GetMapping
    public Flux<TipoSolicitudDTO> getAllTipoSolicitudes() {
        return tipoSolicitudService.findAllTipoSolicitud();
    }
}
