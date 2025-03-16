package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.ContactoMapper;
import com.experis.gestion.solicitudes.app.model.Contacto;
import com.experis.gestion.solicitudes.app.model.dto.ContactoDTO;
import com.experis.gestion.solicitudes.app.repository.ContactoRepository;
import com.experis.gestion.solicitudes.app.service.ContactoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del servicio {@link ContactoService}.
 * Proporciona la lógica de negocio para la gestión de contactos.
 */
@Service
public class ContactoServiceImpl implements ContactoService {

    private static final Logger logger = LoggerFactory.getLogger(ContactoServiceImpl.class);

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private ContactoMapper contactoMapper;

    /**
     * Guarda un nuevo contacto en la base de datos.
     * Si ocurre un error durante la operación, se captura y registra.
     *
     * @param contactoDTO Datos del contacto a guardar.
     * @return Mono con el contacto guardado en formato {@link ContactoDTO}.
     */
    public Mono<ContactoDTO> saveContacto(ContactoDTO contactoDTO) {
        try {
            Contacto contacto = contactoMapper.toEntity(contactoDTO);
            return contactoRepository.save(contacto)
                    .map(contactoMapper::toDto)
                    .onErrorResume(e -> {
                        logger.error("Error al guardar el contacto", e);
                        return Mono.error(new RuntimeException("No se pudo guardar el contacto"));
                    });
        } catch (Exception e) {
            logger.error("Excepción inesperada al guardar el contacto", e);
            return Mono.error(new RuntimeException("Error inesperado"));
        }
    }

    /**
     * Obtiene todos los contactos almacenados en la base de datos.
     * Maneja posibles errores en la consulta.
     *
     * @return Flux con la lista de contactos en formato {@link ContactoDTO}.
     */
    public Flux<ContactoDTO> getAllContactos() {
        return contactoRepository.findAll()
                .map(contactoMapper::toDto)
                .onErrorResume(e -> {
                    logger.error("Error al obtener todos los contactos", e);
                    return Flux.error(new RuntimeException("No se pudieron recuperar los contactos"));
                });
    }

    /**
     * Elimina un contacto por su ID.
     * Si ocurre un error durante la eliminación, se captura y maneja.
     *
     * @param id Identificador del contacto a eliminar.
     * @return Mono vacío cuando se completa la eliminación.
     */
    public Mono<Void> deleteContacto(Long id) {
        return contactoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Contacto no encontrado")))
                .flatMap(contacto -> contactoRepository.deleteById(id))
                .onErrorResume(e -> {
                    logger.error("Error al eliminar el contacto con ID: {}", id, e);
                    return Mono.error(new RuntimeException("No se pudo eliminar el contacto"));
                });
    }

    /**
     * Obtiene los contactos asociados a una solicitud específica.
     *
     * @param solicitudId Identificador de la solicitud.
     * @return Flux con la lista de contactos relacionados en formato {@link ContactoDTO}.
     */
    @Override
    public Flux<ContactoDTO> findBySolicitudId(Long solicitudId) {
        return contactoRepository.findBySolicitudId(solicitudId)
                .map(contactoMapper::toDto)
                .onErrorResume(e -> {
                    logger.error("Error al obtener contactos para la solicitud ID: {}", solicitudId, e);
                    return Flux.error(new RuntimeException("No se pudieron recuperar los contactos de la solicitud"));
                });
    }
}
