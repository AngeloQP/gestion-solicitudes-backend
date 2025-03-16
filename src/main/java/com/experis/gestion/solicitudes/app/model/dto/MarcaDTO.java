package com.experis.gestion.solicitudes.app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Marca", description = "DTO para representar una marca")
public class MarcaDTO {

    @Schema(description = "ID único de la marca", example = "1")
    private Long id;

    @Schema(description = "Descripción de la marca", example = "Apple")
    private String descripcion;
}
