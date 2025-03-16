package com.experis.gestion.solicitudes.app.service;

import com.experis.gestion.solicitudes.app.model.dto.ContactoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContactoService {
    Mono<ContactoDTO> saveContacto(ContactoDTO contactoDTO);
    Flux<ContactoDTO> getAllContactos();
    Mono<Void> deleteContacto(Long id);
    Flux<ContactoDTO> findBySolicitudId(Long solicitudId);
}
