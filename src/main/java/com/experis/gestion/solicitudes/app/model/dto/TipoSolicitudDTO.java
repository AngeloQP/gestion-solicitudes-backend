package com.experis.gestion.solicitudes.app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TipoSolicitud", description = "DTO para representar un tipo de solicitud")
public class TipoSolicitudDTO {

    @Schema(description = "ID único del tipo de solicitud", example = "1")
    private Long id;

    @Schema(description = "Descripción del tipo de solicitud", example = "Garantía extendida")
    private String descripcion;
}
