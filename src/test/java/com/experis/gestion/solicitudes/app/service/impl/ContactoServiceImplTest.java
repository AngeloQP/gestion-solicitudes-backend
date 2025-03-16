package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.ContactoMapper;
import com.experis.gestion.solicitudes.app.model.Contacto;
import com.experis.gestion.solicitudes.app.model.dto.ContactoDTO;
import com.experis.gestion.solicitudes.app.repository.ContactoRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactoServiceImplTest {

    @Mock
    private ContactoRepository contactoRepository;

    @Mock
    private ContactoMapper contactoMapper;

    @InjectMocks
    private ContactoServiceImpl contactoService;

    private Contacto contacto;
    private ContactoDTO contactoDTO;

    @BeforeEach
    void setUp() {
        contacto = new Contacto();
        contacto.setId(1L);
        contacto.setNombreContacto("Angelo Querevalu");

        contactoDTO = new ContactoDTO();
        contactoDTO.setId(1L);
        contactoDTO.setNombreContacto("Angelo Querevalu");
    }

    @Test
    void testSaveContacto_Success() {
        when(contactoMapper.toEntity(contactoDTO)).thenReturn(contacto);
        when(contactoRepository.save(contacto)).thenReturn(Mono.just(contacto));
        when(contactoMapper.toDto(contacto)).thenReturn(contactoDTO);

        Mono<ContactoDTO> result = contactoService.saveContacto(contactoDTO);

        StepVerifier.create(result)
                .expectNext(contactoDTO)
                .verifyComplete();

        verify(contactoRepository, times(1)).save(contacto);
    }

    @Test
    void testSaveContacto_Error() {
        when(contactoMapper.toEntity(contactoDTO)).thenReturn(contacto);
        when(contactoRepository.save(contacto)).thenReturn(Mono.error(new RuntimeException("Error DB")));

        Mono<ContactoDTO> result = contactoService.saveContacto(contactoDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("No se pudo guardar el contacto"))
                .verify();

        verify(contactoRepository, times(1)).save(contacto);
    }

    @Test
    void testSaveContacto_UnexpectedException() {
        ContactoDTO contactoDTO = new ContactoDTO();

        when(contactoMapper.toEntity(any())).thenThrow(new RuntimeException("Error inesperado en el mapeo"));

        Mono<ContactoDTO> result = contactoService.saveContacto(contactoDTO);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertTrue(error instanceof RuntimeException);
                    assertEquals("Error inesperado", error.getMessage());
                })
                .verify();

        verify(contactoMapper, times(1)).toEntity(any());
        verify(contactoRepository, never()).save(any());
    }


    @Test
    void testGetAllContactos_Success() {
        when(contactoRepository.findAll()).thenReturn(Flux.just(contacto));
        when(contactoMapper.toDto(contacto)).thenReturn(contactoDTO);

        Flux<ContactoDTO> result = contactoService.getAllContactos();

        StepVerifier.create(result)
                .expectNext(contactoDTO)
                .verifyComplete();

        verify(contactoRepository, times(1)).findAll();
    }

    @Test
    void testGetAllContactos_Error() {
        when(contactoRepository.findAll()).thenReturn(Flux.error(new RuntimeException("Error DB")));

        Flux<ContactoDTO> result = contactoService.getAllContactos();

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("No se pudieron recuperar los contactos"))
                .verify();

        verify(contactoRepository, times(1)).findAll();
    }

    @Test
    void testDeleteContacto_Success() {
        when(contactoRepository.findById(1L)).thenReturn(Mono.just(contacto));
        when(contactoRepository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = contactoService.deleteContacto(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(contactoRepository, times(1)).findById(1L);
        verify(contactoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteContacto_NotFound() {
        when(contactoRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = contactoService.deleteContacto(1L);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertTrue(error instanceof RuntimeException);
                    assertTrue(error.getMessage().equals("Contacto no encontrado") ||
                            error.getMessage().equals("No se pudo eliminar el contacto"));
                })
                .verify();

        verify(contactoRepository, times(1)).findById(1L);
        verify(contactoRepository, never()).deleteById(anyLong());
    }


    @Test
    void testFindBySolicitudId_Success() {
        when(contactoRepository.findBySolicitudId(100L)).thenReturn(Flux.just(contacto));
        when(contactoMapper.toDto(contacto)).thenReturn(contactoDTO);

        Flux<ContactoDTO> result = contactoService.findBySolicitudId(100L);

        StepVerifier.create(result)
                .expectNext(contactoDTO)
                .verifyComplete();

        verify(contactoRepository, times(1)).findBySolicitudId(100L);
    }

    @Test
    void testFindBySolicitudId_Error() {
        when(contactoRepository.findBySolicitudId(100L)).thenReturn(Flux.error(new RuntimeException("Error DB")));

        Flux<ContactoDTO> result = contactoService.findBySolicitudId(100L);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("No se pudieron recuperar los contactos de la solicitud"))
                .verify();

        verify(contactoRepository, times(1)).findBySolicitudId(100L);
    }
}
