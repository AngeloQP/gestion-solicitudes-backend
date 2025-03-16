package com.experis.gestion.solicitudes.app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Solicitud", description = "DTO para representar una solicitud")
public class SolicitudDTO {

    @Schema(description = "ID único de la solicitud", example = "1001")
    private Long id;

    @Schema(description = "Código identificador de la solicitud", example = "SOL-20240316")
    private String codigo;

    @Schema(description = "ID de la marca asociada", example = "1")
    private Long marcaId;

    @Schema(description = "Descripción de la marca", example = "Apple")
    private String marcaDescripcion;

    @Schema(description = "ID del tipo de solicitud", example = "2")
    private Long tipoSolicitudId;

    @Schema(description = "Descripción del tipo de solicitud", example = "Garantía extendida")
    private String tipoSolicitudDescripcion;

    @Schema(description = "Nombre del contacto asociado a la solicitud", example = "Juan Pérez")
    private String nombreContacto;

    @Schema(description = "Número de contacto", example = "+51987654321")
    private String numeroContacto;

    @Schema(description = "Fecha de envío de la solicitud", example = "2025-03-16")
    private LocalDate fechaEnvio;
}
