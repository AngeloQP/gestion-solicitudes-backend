package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.TipoSolicitudMapper;
import com.experis.gestion.solicitudes.app.model.TipoSolicitud;
import com.experis.gestion.solicitudes.app.model.dto.TipoSolicitudDTO;
import com.experis.gestion.solicitudes.app.repository.TipoSolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoSolicitudServiceImplTest {

    @Mock
    private TipoSolicitudRepository tipoSolicitudRepository;

    @Mock
    private TipoSolicitudMapper tipoSolicitudMapper;

    @InjectMocks
    private TipoSolicitudServiceImpl tipoSolicitudService;

    private TipoSolicitud tipoSolicitud1, tipoSolicitud2;
    private TipoSolicitudDTO tipoSolicitudDTO1, tipoSolicitudDTO2;

    @BeforeEach
    void setUp() {
        tipoSolicitud1 = new TipoSolicitud(1L, "Reparacion");
        tipoSolicitud2 = new TipoSolicitud(2L, "Mantenimiento");

        tipoSolicitudDTO1 = new TipoSolicitudDTO(1L, "Reparacion");
        tipoSolicitudDTO2 = new TipoSolicitudDTO(2L, "Mantenimiento");
    }

    @Test
    void testFindAllTipoSolicitud_Success() {
        when(tipoSolicitudRepository.findAll()).thenReturn(Flux.just(tipoSolicitud1, tipoSolicitud2));

        when(tipoSolicitudMapper.toDTO(tipoSolicitud1)).thenReturn(tipoSolicitudDTO1);
        when(tipoSolicitudMapper.toDTO(tipoSolicitud2)).thenReturn(tipoSolicitudDTO2);

        StepVerifier.create(tipoSolicitudService.findAllTipoSolicitud())
                .expectNext(tipoSolicitudDTO1, tipoSolicitudDTO2)
                .verifyComplete();

        verify(tipoSolicitudRepository, times(1)).findAll();
        verify(tipoSolicitudMapper, times(2)).toDTO(any());
    }

    @Test
    void testFindAllTipoSolicitud_Error() {
        when(tipoSolicitudRepository.findAll()).thenReturn(Flux.error(new RuntimeException("DB Error")));

        StepVerifier.create(tipoSolicitudService.findAllTipoSolicitud())
                .expectErrorSatisfies(error -> {
                    assert error instanceof RuntimeException;
                    assert "No se pudieron recuperar los tipos de solicitud.".equals(error.getMessage());
                })
                .verify();

        verify(tipoSolicitudRepository, times(1)).findAll();
    }
}
