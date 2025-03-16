package com.experis.gestion.solicitudes.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("tipo_solicitud")
public class TipoSolicitud {
    @Id
    private Long id;

    @Column("descripcion")
    private String descripcion;
}
