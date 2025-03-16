package com.experis.gestion.solicitudes.app.mapper;

import com.experis.gestion.solicitudes.app.model.Contacto;
import com.experis.gestion.solicitudes.app.model.dto.ContactoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactoMapper {

    ContactoDTO toDto(Contacto contacto);
    Contacto toEntity(ContactoDTO contactoDTO);
    List<ContactoDTO> toDtoList(List<Contacto> contactos);
    List<Contacto> toEntityList(List<ContactoDTO> contactoDTOs);
}
