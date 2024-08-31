package io.sisin.sisin.rest;

import io.sisin.sisin.model.UnidadDTO;
import io.sisin.sisin.service.UnidadService;
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
@RequestMapping(value = "/api/unidads", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnidadResource {

    private final UnidadService unidadService;

    public UnidadResource(final UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @GetMapping
    public ResponseEntity<List<UnidadDTO>> getAllUnidads() {
        return ResponseEntity.ok(unidadService.findAll());
    }

    @GetMapping("/{undId}")
    public ResponseEntity<UnidadDTO> getUnidad(@PathVariable(name = "undId") final Integer undId) {
        return ResponseEntity.ok(unidadService.get(undId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUnidad(@RequestBody @Valid final UnidadDTO unidadDTO) {
        final Integer createdUndId = unidadService.create(unidadDTO);
        return new ResponseEntity<>(createdUndId, HttpStatus.CREATED);
    }

    @PutMapping("/{undId}")
    public ResponseEntity<Integer> updateUnidad(@PathVariable(name = "undId") final Integer undId,
            @RequestBody @Valid final UnidadDTO unidadDTO) {
        unidadService.update(undId, unidadDTO);
        return ResponseEntity.ok(undId);
    }

    @DeleteMapping("/{undId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUnidad(@PathVariable(name = "undId") final Integer undId) {
        final ReferencedWarning referencedWarning = unidadService.getReferencedWarning(undId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        unidadService.delete(undId);
        return ResponseEntity.noContent().build();
    }

}
