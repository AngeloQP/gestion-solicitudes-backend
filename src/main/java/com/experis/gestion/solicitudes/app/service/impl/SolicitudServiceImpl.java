package com.experis.gestion.solicitudes.app.service.impl;

import com.experis.gestion.solicitudes.app.mapper.SolicitudMapper;
import com.experis.gestion.solicitudes.app.model.Marca;
import com.experis.gestion.solicitudes.app.model.Solicitud;
import com.experis.gestion.solicitudes.app.model.TipoSolicitud;
import com.experis.gestion.solicitudes.app.model.dto.SolicitudDTO;
import com.experis.gestion.solicitudes.app.repository.MarcaRepository;
import com.experis.gestion.solicitudes.app.repository.SolicitudRepository;
import com.experis.gestion.solicitudes.app.repository.TipoSolicitudRepository;
import com.experis.gestion.solicitudes.app.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * Implementación del servicio para gestionar las solicitudes.
 */
@Service
public class SolicitudServiceImpl implements SolicitudService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private TipoSolicitudRepository tipoSolicitudRepository;

    /**
     * Guarda una nueva solicitud en la base de datos.
     * @param solicitudDTO DTO con la información de la solicitud.
     * @return Mono<SolicitudDTO> con la solicitud guardada.
     */
    @Override
    public Mono<SolicitudDTO> saveSolicitud(SolicitudDTO solicitudDTO) {
        return generarCodigoSolicitud()
                .map(codigo -> {
                    solicitudDTO.setCodigo(codigo);
                    return solicitudMapper.toEntity(solicitudDTO);
                })
                .flatMap(solicitudRepository::save)
                .map(solicitudMapper::toDTO)
                .doOnError(e -> logger.error("Error al guardar la solicitud", e));
    }

    /**
     * Genera un código único para una nueva solicitud.
     * @return Mono<String> con el código generado.
     */
    private Mono<String> generarCodigoSolicitud() {
        return solicitudRepository.findLastCodigo()
                .map(lastCode -> {
                    int numero = Integer.parseInt(lastCode.substring(3));
                    return String.format("SOL%03d", numero + 1);
                })
                .defaultIfEmpty("SOL001")
                .doOnError(e -> logger.error("Error al generar código de solicitud", e));
    }

    /**
     * Obtiene todas las solicitudes almacenadas en la base de datos.
     * @return Flux<SolicitudDTO> con la lista de solicitudes.
     */
    @Override
    public Flux<SolicitudDTO> getAllSolicitudes() {
        return solicitudRepository.findAll()
                .flatMap(solicitud ->
                        Mono.zip(
                                marcaRepository.findById(solicitud.getMarcaId())
                                        .map(Marca::getDescripcion)
                                        .defaultIfEmpty("Sin marca"),
                                tipoSolicitudRepository.findById(solicitud.getTipoSolicitudId())
                                        .map(TipoSolicitud::getDescripcion)
                                        .defaultIfEmpty("Sin tipo de solicitud")
                        ).map(tuple -> {
                            SolicitudDTO solicitudDTO = solicitudMapper.toDTO(solicitud);
                            solicitudDTO.setMarcaDescripcion(tuple.getT1());
                            solicitudDTO.setTipoSolicitudDescripcion(tuple.getT2());
                            return solicitudDTO;
                        })
                ).doOnError(e -> logger.error("Error al obtener todas las solicitudes", e));
    }



    /**
     * Obtiene solicitudes según los filtros aplicados.
     * @param startDate Fecha de inicio del filtro.
     * @param endDate Fecha de fin del filtro.
     * @param tipoSolicitud Tipo de solicitud a filtrar.
     * @return Flux<SolicitudDTO> con las solicitudes filtradas.
     */
    @Override
    public Flux<SolicitudDTO> getSolicitudesByFilters(LocalDate startDate, LocalDate endDate, String tipoSolicitud) {
        Flux<Solicitud> solicitudes;
        if (startDate != null && endDate != null) {
            solicitudes = solicitudRepository.findByFechaEnvioBetween(startDate, endDate);
        } else if (tipoSolicitud != null) {
            solicitudes = solicitudRepository.findByTipoSolicitudDescripcion(tipoSolicitud);
        } else {
            solicitudes = solicitudRepository.findAll();
        }
        return solicitudes.map(solicitudMapper::toDTO)
                .doOnError(e -> logger.error("Error al obtener solicitudes con filtros", e));
    }

    /**
     * Obtiene una solicitud por su código.
     * @param codigo Código de la solicitud.
     * @return Mono<SolicitudDTO> con la solicitud encontrada.
     */
    @Override
    public Mono<SolicitudDTO> getSolicitudByCodigo(String codigo) {
        return solicitudRepository.findByCodigo(codigo)
                .flatMap(solicitud -> Mono.zip(
                        marcaRepository.findMarcaById(solicitud.getMarcaId())
                                .map(Marca::getDescripcion)
                                .defaultIfEmpty("Sin marca"),
                        tipoSolicitudRepository.findTipoSolicitudById(solicitud.getTipoSolicitudId())
                                .map(TipoSolicitud::getDescripcion)
                                .defaultIfEmpty("Sin tipo de solicitud")
                ).map(tuple -> {
                    SolicitudDTO solicitudDTO = solicitudMapper.toDTO(solicitud);
                    solicitudDTO.setMarcaDescripcion(tuple.getT1());
                    solicitudDTO.setTipoSolicitudDescripcion(tuple.getT2());
                    return solicitudDTO;
                }))
                .doOnError(e -> logger.error("Error al obtener solicitud por código", e));
    }


    @Override
    public Mono<SolicitudDTO> updateSolicitud(Long id, SolicitudDTO solicitudDTO) {
        return null;
    }

    /**
     * Elimina una solicitud de la base de datos.
     * @param id ID de la solicitud a eliminar.
     * @return Mono<Void> indicando que la operación se ha completado.
     */
    @Override
    public Mono<Void> deleteSolicitud(Long id) {
        return solicitudRepository.deleteById(id)
                .doOnError(e -> logger.error("Error al eliminar solicitud con ID: " + id, e));
    }
    /**
     * Exporta todas las solicitudes a un archivo CSV.
     * Si ocurre un error durante la generación del archivo, se maneja con una excepción.
     *
     * @return Mono<ResponseEntity<Resource>> con el archivo CSV generado o un error si ocurre una excepción.
     */
    @Override
    public Mono<ResponseEntity<ByteArrayResource>> exportToCSV() {
        return solicitudRepository.findAll()
                .flatMap(solicitud -> Mono.zip(
                        marcaRepository.findMarcaById(solicitud.getMarcaId())
                                .map(Marca::getDescripcion)
                                .defaultIfEmpty("Sin marca"),
                        tipoSolicitudRepository.findTipoSolicitudById(solicitud.getTipoSolicitudId())
                                .map(TipoSolicitud::getDescripcion)
                                .defaultIfEmpty("Sin tipo de solicitud")
                ).map(tuple -> {
                    String marcaDescripcion = tuple.getT1();
                    String tipoSolicitudDescripcion = tuple.getT2();

                    return String.format("=\"%d\";=\"%s\";=\"%s\";=\"%s\";=\"%s\";=\"%s\";=\"%s\"\n",
                            solicitud.getId(),
                            solicitud.getCodigo(),
                            marcaDescripcion,
                            tipoSolicitudDescripcion,
                            solicitud.getFechaEnvio().toString(),
                            solicitud.getNombreContacto(),
                            solicitud.getNumeroContacto());
                }))
                .collectList()
                .map(csvRows -> {
                    try {
                        StringBuilder csvBuilder = new StringBuilder();
                        csvBuilder.append("\uFEFF");
                        csvBuilder.append("ID;Código;Marca;Tipo de Solicitud;Fecha de Envío;Nombre de Contacto;Número de Contacto\n");

                        csvRows.forEach(csvBuilder::append);

                        ByteArrayResource resource = new ByteArrayResource(csvBuilder.toString().getBytes(StandardCharsets.UTF_8));

                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=solicitudes.csv")
                                .contentType(MediaType.parseMediaType("text/csv"))
                                .body(resource);
                    } catch (Exception e) {
                        logger.error("Error al exportar solicitudes a CSV", e);
                        throw new RuntimeException("Error al generar el archivo CSV", e);
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error inesperado en exportToCSV", e);
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }

}
