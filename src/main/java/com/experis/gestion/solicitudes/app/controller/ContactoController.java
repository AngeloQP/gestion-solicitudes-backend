package com.experis.gestion.solicitudes.app.controller;

import com.experis.gestion.solicitudes.app.model.dto.ContactoDTO;
import com.experis.gestion.solicitudes.app.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contactos")
@Tag(name = "Contactos", description = "API para la gestión de contactos asociados a solicitudes")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @Operation(summary = "Crear un nuevo contacto", description = "Registra un nuevo contacto en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public Mono<ContactoDTO> createContacto(@RequestBody ContactoDTO contactoDTO) {
        return contactoService.saveContacto(contactoDTO);
    }

    @Operation(summary = "Obtener todos los contactos", description = "Devuelve una lista de todos los contactos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contactos obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public Flux<ContactoDTO> getAllContactos() {
        return contactoService.getAllContactos();
    }

    @Operation(summary = "Eliminar un contacto", description = "Elimina un contacto por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contacto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contacto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteContacto(@PathVariable Long id) {
        return contactoService.deleteContacto(id);
    }

    @Operation(summary = "Obtener contactos por solicitud", description = "Devuelve los contactos asociados a una solicitud específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contactos obtenida con éxito"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{solicitudId}")
    public Flux<ContactoDTO> obtenerContactosPorSolicitud(@PathVariable Long solicitudId) {
        return contactoService.findBySolicitudId(solicitudId);
    }
}
