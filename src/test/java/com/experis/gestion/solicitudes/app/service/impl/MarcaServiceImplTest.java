package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.MarcaMapper;
import com.experis.gestion.solicitudes.app.model.Marca;
import com.experis.gestion.solicitudes.app.model.dto.MarcaDTO;
import com.experis.gestion.solicitudes.app.repository.MarcaRepository;
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
public class MarcaServiceImplTest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private MarcaMapper marcaMapper;

    @InjectMocks
    private MarcaServiceImpl marcaService;

    private Marca marca1, marca2;
    private MarcaDTO marcaDTO1, marcaDTO2;

    @BeforeEach
    void setUp() {
        marca1 = new Marca(1L, "Toyota");
        marca2 = new Marca(2L, "Ford");

        marcaDTO1 = new MarcaDTO(1L, "Toyota");
        marcaDTO2 = new MarcaDTO(2L, "Ford");
    }

    @Test
    void testFindAllMarcas_Success() {
        when(marcaRepository.findAllMarcas()).thenReturn(Flux.just(marca1, marca2));
        when(marcaMapper.toDTO(marca1)).thenReturn(marcaDTO1);
        when(marcaMapper.toDTO(marca2)).thenReturn(marcaDTO2);

        StepVerifier.create(marcaService.findAllMarcas())
                .expectNext(marcaDTO1, marcaDTO2)
                .verifyComplete();

        verify(marcaRepository, times(1)).findAllMarcas();
        verify(marcaMapper, times(2)).toDTO(any());
    }

    @Test
    void testFindAllMarcas_Error() {
        when(marcaRepository.findAllMarcas()).thenReturn(Flux.error(new RuntimeException("DB Error")));

        StepVerifier.create(marcaService.findAllMarcas())
                .expectErrorSatisfies(error -> {
                    assert error instanceof RuntimeException;
                    assert "No se pudieron recuperar las marcas.".equals(error.getMessage());
                })
                .verify();

        verify(marcaRepository, times(1)).findAllMarcas();
    }
}
