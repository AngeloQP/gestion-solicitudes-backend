package com.experis.gestion.solicitudes.app.mapper;

import com.experis.gestion.solicitudes.app.model.Solicitud;
import com.experis.gestion.solicitudes.app.model.dto.SolicitudDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

    SolicitudDTO toDTO(Solicitud solicitud);
    Solicitud toEntity(SolicitudDTO solicitudDTO);
    List<SolicitudDTO> toDTOList(List<Solicitud> solicitudes);
    List<Solicitud> toEntityList(List<SolicitudDTO> solicitudDTOs);
}
