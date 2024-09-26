package io.sisin.sisin.rest;

import io.sisin.sisin.model.TransaccionTipoEventoDTO;
import io.sisin.sisin.service.TransaccionTipoEventoService;
import io.sisin.sisin.util.ReferencedException;
import io.sisin.sisin.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/transaccionTipoEvento", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionTipoEventoResource {

    private final TransaccionTipoEventoService transaccionTipoEventoService;

    public TransaccionTipoEventoResource(
            final TransaccionTipoEventoService transaccionTipoEventoService) {
        this.transaccionTipoEventoService = transaccionTipoEventoService;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionTipoEventoDTO>> getAllTransaccionTipoEvento() {
        return ResponseEntity.ok(transaccionTipoEventoService.findAll());
    }

    @GetMapping("/{tteId}")
    public ResponseEntity<TransaccionTipoEventoDTO> getTransaccionTipoEvento(
            @PathVariable(name = "tteId") final Integer tteId) {
        return ResponseEntity.ok(transaccionTipoEventoService.get(tteId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTransaccionTipoEvento(
            @RequestBody @Valid final TransaccionTipoEventoDTO transaccionTipoEventoDTO) {
        final Integer createdTteId = transaccionTipoEventoService.create(transaccionTipoEventoDTO);
        return new ResponseEntity<>(createdTteId, HttpStatus.CREATED);
    }

    @PutMapping("/{tteId}")
    public ResponseEntity<Integer> updateTransaccionTipoEvento(
            @PathVariable(name = "tteId") final Integer tteId,
            @RequestBody @Valid final TransaccionTipoEventoDTO transaccionTipoEventoDTO) {
        transaccionTipoEventoService.update(tteId, transaccionTipoEventoDTO);
        return ResponseEntity.ok(tteId);
    }

    @DeleteMapping("/{tteId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaccionTipoEvento(
            @PathVariable(name = "tteId") final Integer tteId) {
        final ReferencedWarning referencedWarning = transaccionTipoEventoService.getReferencedWarning(tteId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        transaccionTipoEventoService.delete(tteId);
        return ResponseEntity.noContent().build();
    }

}
