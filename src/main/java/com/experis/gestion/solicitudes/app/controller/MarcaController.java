package com.experis.gestion.solicitudes.app.controller;

import com.experis.gestion.solicitudes.app.model.dto.MarcaDTO;
import com.experis.gestion.solicitudes.app.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/marca")
@Tag(name = "Marcas", description = "API para la gestión de marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @Operation(summary = "Obtener todas las marcas", description = "Devuelve una lista de todas las marcas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public Flux<MarcaDTO> getAllMarca() {
        return marcaService.findAllMarcas();
    }
}
