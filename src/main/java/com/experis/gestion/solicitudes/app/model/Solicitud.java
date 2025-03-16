package com.experis.gestion.solicitudes.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Table("solicitudes")
public class Solicitud {

    @Id
    private Long id;

    @Column("codigo")
    private String codigo;

    @Column("marca_id")
    private Long marcaId;

    @Column("tipo_solicitud_id")
    private Long tipoSolicitudId;

    @Column("fecha_envio")
    private LocalDate fechaEnvio;

    @Column("nombre_contacto")
    private String nombreContacto;

    @Column("numero_contacto")
    private String numeroContacto;

    @Transient
    private List<Contacto> contactos;
}
