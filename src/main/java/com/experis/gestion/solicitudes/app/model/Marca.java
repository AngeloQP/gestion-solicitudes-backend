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
@Table("marca")
public class Marca {
    @Id
    private Long id;

    @Column("descripcion")
    private String descripcion;
}
