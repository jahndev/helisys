package io.sisin.sisin.rest;

import io.sisin.sisin.model.TrasaccionTipoEventoDTO;
import io.sisin.sisin.service.TrasaccionTipoEventoService;
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
@RequestMapping(value = "/api/trasaccionTipoEventos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrasaccionTipoEventoResource {

    private final TrasaccionTipoEventoService trasaccionTipoEventoService;

    public TrasaccionTipoEventoResource(
            final TrasaccionTipoEventoService trasaccionTipoEventoService) {
        this.trasaccionTipoEventoService = trasaccionTipoEventoService;
    }

    @GetMapping
    public ResponseEntity<List<TrasaccionTipoEventoDTO>> getAllTrasaccionTipoEventos() {
        return ResponseEntity.ok(trasaccionTipoEventoService.findAll());
    }

    @GetMapping("/{tteId}")
    public ResponseEntity<TrasaccionTipoEventoDTO> getTrasaccionTipoEvento(
            @PathVariable(name = "tteId") final Integer tteId) {
        return ResponseEntity.ok(trasaccionTipoEventoService.get(tteId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTrasaccionTipoEvento(
            @RequestBody @Valid final TrasaccionTipoEventoDTO trasaccionTipoEventoDTO) {
        final Integer createdTteId = trasaccionTipoEventoService.create(trasaccionTipoEventoDTO);
        return new ResponseEntity<>(createdTteId, HttpStatus.CREATED);
    }

    @PutMapping("/{tteId}")
    public ResponseEntity<Integer> updateTrasaccionTipoEvento(
            @PathVariable(name = "tteId") final Integer tteId,
            @RequestBody @Valid final TrasaccionTipoEventoDTO trasaccionTipoEventoDTO) {
        trasaccionTipoEventoService.update(tteId, trasaccionTipoEventoDTO);
        return ResponseEntity.ok(tteId);
    }

    @DeleteMapping("/{tteId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTrasaccionTipoEvento(
            @PathVariable(name = "tteId") final Integer tteId) {
        final ReferencedWarning referencedWarning = trasaccionTipoEventoService.getReferencedWarning(tteId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        trasaccionTipoEventoService.delete(tteId);
        return ResponseEntity.noContent().build();
    }

}
