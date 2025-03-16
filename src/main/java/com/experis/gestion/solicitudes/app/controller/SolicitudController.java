package com.experis.gestion.solicitudes.app.controller;

import com.experis.gestion.solicitudes.app.model.dto.SolicitudDTO;
import com.experis.gestion.solicitudes.app.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/solicitudes")
@Tag(name = "Solicitudes", description = "API para la gestión de solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Operation(summary = "Crear una nueva solicitud", description = "Registra una nueva solicitud en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados")
    })
    @PostMapping
    public Mono<SolicitudDTO> createSolicitud(@RequestBody SolicitudDTO solicitudDTO) {
        return solicitudService.saveSolicitud(solicitudDTO);
    }

    @Operation(summary = "Obtener todas las solicitudes", description = "Devuelve una lista de todas las solicitudes registradas")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida con éxito")
    @GetMapping
    public Flux<SolicitudDTO> getAllSolicitudes() {
        return solicitudService.getAllSolicitudes();
    }

    @Operation(summary = "Filtrar solicitudes", description = "Permite filtrar solicitudes por fecha de inicio, fin y tipo de solicitud")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes filtrada correctamente")
    @GetMapping("/filter")
    public Flux<SolicitudDTO> getSolicitudesByFilters(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String tipoSolicitud) {
        return solicitudService.getSolicitudesByFilters(startDate, endDate, tipoSolicitud);
    }

    @Operation(summary = "Obtener una solicitud por código", description = "Devuelve una solicitud específica basada en su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @GetMapping("/{codigo}")
    public Mono<SolicitudDTO> getSolicitudByCodigo(@PathVariable String codigo) {
        return solicitudService.getSolicitudByCodigo(codigo);
    }

    @Operation(summary = "Actualizar una solicitud", description = "Modifica los datos de una solicitud existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}")
    public Mono<SolicitudDTO> updateSolicitud(@PathVariable Long id, @RequestBody SolicitudDTO solicitudDTO) {
        return solicitudService.updateSolicitud(id, solicitudDTO);
    }

    @Operation(summary = "Eliminar una solicitud", description = "Elimina una solicitud del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Solicitud eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteSolicitud(@PathVariable Long id) {
        return solicitudService.deleteSolicitud(id);
    }

    @Operation(summary = "Exportar solicitudes a CSV", description = "Genera y descarga un archivo CSV con todas las solicitudes registradas")
    @ApiResponse(responseCode = "200", description = "Archivo CSV generado correctamente")
    @GetMapping("/export")
    public Mono<ResponseEntity<ByteArrayResource>> exportToCSV() {
        return solicitudService.exportToCSV();
    }
}
