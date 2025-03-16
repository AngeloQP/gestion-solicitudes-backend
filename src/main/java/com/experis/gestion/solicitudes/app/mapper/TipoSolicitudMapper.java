package com.experis.gestion.solicitudes.app.mapper;

import com.experis.gestion.solicitudes.app.model.TipoSolicitud;
import com.experis.gestion.solicitudes.app.model.dto.TipoSolicitudDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoSolicitudMapper {

    TipoSolicitudDTO toDTO(TipoSolicitud tipoSolicitud);
    TipoSolicitud toEntity(TipoSolicitudDTO tipoSolicitudDTO);
    List<TipoSolicitudDTO> toDTOList(List<TipoSolicitud> tipoSolicitudList);
    List<TipoSolicitud> toEntityList(List<TipoSolicitudDTO> tipoSolicitudDTOList);
}
