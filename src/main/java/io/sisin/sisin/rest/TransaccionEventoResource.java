package io.sisin.sisin.rest;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.TransaccionTipoEvento;
import io.sisin.sisin.model.TransaccionEventoDTO;
import io.sisin.sisin.repos.AeronaveRepository;
import io.sisin.sisin.repos.TransaccionTipoEventoRepository;
import io.sisin.sisin.service.TransaccionEventoService;
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
@RequestMapping(value = "/api/transaccionEvento", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionEventoResource {

    private final TransaccionEventoService transaccionEventoService;
    private final TransaccionTipoEventoRepository transaccionTipoEventoRepository;
    private final AeronaveRepository aeronaveRepository;

    public TransaccionEventoResource(final TransaccionEventoService transaccionEventoService,
            final TransaccionTipoEventoRepository transaccionTipoEventoRepository,
            final AeronaveRepository aeronaveRepository) {
        this.transaccionEventoService = transaccionEventoService;
        this.transaccionTipoEventoRepository = transaccionTipoEventoRepository;
        this.aeronaveRepository = aeronaveRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionEventoDTO>> getAllTransaccionEvento() {
        return ResponseEntity.ok(transaccionEventoService.findAll());
    }

    @GetMapping("/{tvoId}")
    public ResponseEntity<TransaccionEventoDTO> getTransaccionEvento(
            @PathVariable(name = "tvoId") final Integer tvoId) {
        return ResponseEntity.ok(transaccionEventoService.get(tvoId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTransaccionEvento(
            @RequestBody @Valid final TransaccionEventoDTO transaccionEventoDTO) {
        final Integer createdTvoId = transaccionEventoService.create(transaccionEventoDTO);
        return new ResponseEntity<>(createdTvoId, HttpStatus.CREATED);
    }

    @PutMapping("/{tvoId}")
    public ResponseEntity<Integer> updateTransaccionEvento(
            @PathVariable(name = "tvoId") final Integer tvoId,
            @RequestBody @Valid final TransaccionEventoDTO transaccionEventoDTO) {
        transaccionEventoService.update(tvoId, transaccionEventoDTO);
        return ResponseEntity.ok(tvoId);
    }

    @DeleteMapping("/{tvoId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaccionEvento(
            @PathVariable(name = "tvoId") final Integer tvoId) {
        final ReferencedWarning referencedWarning = transaccionEventoService.getReferencedWarning(tvoId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        transaccionEventoService.delete(tvoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tvoTteValues")
    public ResponseEntity<Map<Integer, String>> getTvoTteValues() {
        return ResponseEntity.ok(transaccionTipoEventoRepository.findAll(Sort.by("tteId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TransaccionTipoEvento::getTteId, TransaccionTipoEvento::getTteNombre)));
    }

    @GetMapping("/aeronavesAnvValues")
    public ResponseEntity<Map<Integer, String>> getAeronavesAnvValues() {
        return ResponseEntity.ok(aeronaveRepository.findAll(Sort.by("anvId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Aeronave::getAnvId, Aeronave::getAnvMatricula)));
    }

}
