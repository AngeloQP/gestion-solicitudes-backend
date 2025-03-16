package com.experis.gestion.solicitudes.app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Contacto", description = "DTO para representar un contacto asociado a una solicitud")
public class ContactoDTO {

    @Schema(description = "ID único del contacto", example = "1")
    private Long id;

    @Schema(description = "ID de la solicitud asociada", example = "10")
    private Long solicitudId;

    @Schema(description = "Nombre del contacto", example = "Juan Pérez")
    private String nombreContacto;

    @Schema(description = "Número de contacto", example = "+56987654321")
    private String numeroContacto;
}
