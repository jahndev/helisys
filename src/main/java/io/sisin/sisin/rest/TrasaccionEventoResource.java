package io.sisin.sisin.rest;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.TrasaccionTipoEvento;
import io.sisin.sisin.model.TrasaccionEventoDTO;
import io.sisin.sisin.repos.AeronaveRepository;
import io.sisin.sisin.repos.TrasaccionTipoEventoRepository;
import io.sisin.sisin.service.TrasaccionEventoService;
import io.sisin.sisin.util.CustomCollectors;
import io.sisin.sisin.util.ReferencedException;
import io.sisin.sisin.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/trasaccionEventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrasaccionEventoResource {

    private final TrasaccionEventoService trasaccionEventoService;
    private final TrasaccionTipoEventoRepository trasaccionTipoEventoRepository;
    private final AeronaveRepository aeronaveRepository;

    public TrasaccionEventoResource(final TrasaccionEventoService trasaccionEventoService,
            final TrasaccionTipoEventoRepository trasaccionTipoEventoRepository,
            final AeronaveRepository aeronaveRepository) {
        this.trasaccionEventoService = trasaccionEventoService;
        this.trasaccionTipoEventoRepository = trasaccionTipoEventoRepository;
        this.aeronaveRepository = aeronaveRepository;
    }

    @GetMapping
    public ResponseEntity<List<TrasaccionEventoDTO>> getAllTrasaccionEventos() {
        return ResponseEntity.ok(trasaccionEventoService.findAll());
    }

    @GetMapping("/{tvoId}")
    public ResponseEntity<TrasaccionEventoDTO> getTrasaccionEvento(
            @PathVariable(name = "tvoId") final Integer tvoId) {
        return ResponseEntity.ok(trasaccionEventoService.get(tvoId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTrasaccionEvento(
            @RequestBody @Valid final TrasaccionEventoDTO trasaccionEventoDTO) {
        final Integer createdTvoId = trasaccionEventoService.create(trasaccionEventoDTO);
        return new ResponseEntity<>(createdTvoId, HttpStatus.CREATED);
    }

    @PutMapping("/{tvoId}")
    public ResponseEntity<Integer> updateTrasaccionEvento(
            @PathVariable(name = "tvoId") final Integer tvoId,
            @RequestBody @Valid final TrasaccionEventoDTO trasaccionEventoDTO) {
        trasaccionEventoService.update(tvoId, trasaccionEventoDTO);
        return ResponseEntity.ok(tvoId);
    }

    @DeleteMapping("/{tvoId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTrasaccionEvento(
            @PathVariable(name = "tvoId") final Integer tvoId) {
        final ReferencedWarning referencedWarning = trasaccionEventoService.getReferencedWarning(tvoId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        trasaccionEventoService.delete(tvoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tvoTteValues")
    public ResponseEntity<Map<Integer, String>> getTvoTteValues() {
        return ResponseEntity.ok(trasaccionTipoEventoRepository.findAll(Sort.by("tteId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TrasaccionTipoEvento::getTteId, TrasaccionTipoEvento::getTteNombre)));
    }

    @GetMapping("/aeronavesAnvValues")
    public ResponseEntity<Map<Integer, String>> getAeronavesAnvValues() {
        return ResponseEntity.ok(aeronaveRepository.findAll(Sort.by("anvId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Aeronave::getAnvId, Aeronave::getAnvMatricula)));
    }

}
