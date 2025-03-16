package com.experis.gestion.solicitudes.app.mapper;

import com.experis.gestion.solicitudes.app.model.Marca;
import com.experis.gestion.solicitudes.app.model.dto.MarcaDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MarcaMapper {
    MarcaDTO toDTO (Marca marca);
    Marca toEntity (MarcaDTO marcaDTO);
    List<MarcaDTO> toDTOList(List<Marca> marcas);
    List<Marca> toEntityList(List<MarcaDTO> marcaDTOs);
}
