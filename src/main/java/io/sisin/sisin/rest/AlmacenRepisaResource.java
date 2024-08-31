package io.sisin.sisin.rest;

import io.sisin.sisin.model.AlmacenRepisaDTO;
import io.sisin.sisin.service.AlmacenRepisaService;
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
@RequestMapping(value = "/api/almacenRepisas", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlmacenRepisaResource {

    private final AlmacenRepisaService almacenRepisaService;

    public AlmacenRepisaResource(final AlmacenRepisaService almacenRepisaService) {
        this.almacenRepisaService = almacenRepisaService;
    }

    @GetMapping
    public ResponseEntity<List<AlmacenRepisaDTO>> getAllAlmacenRepisas() {
        return ResponseEntity.ok(almacenRepisaService.findAll());
    }

    @GetMapping("/{amrId}")
    public ResponseEntity<AlmacenRepisaDTO> getAlmacenRepisa(
            @PathVariable(name = "amrId") final Integer amrId) {
        return ResponseEntity.ok(almacenRepisaService.get(amrId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAlmacenRepisa(
            @RequestBody @Valid final AlmacenRepisaDTO almacenRepisaDTO) {
        final Integer createdAmrId = almacenRepisaService.create(almacenRepisaDTO);
        return new ResponseEntity<>(createdAmrId, HttpStatus.CREATED);
    }

    @PutMapping("/{amrId}")
    public ResponseEntity<Integer> updateAlmacenRepisa(
            @PathVariable(name = "amrId") final Integer amrId,
            @RequestBody @Valid final AlmacenRepisaDTO almacenRepisaDTO) {
        almacenRepisaService.update(amrId, almacenRepisaDTO);
        return ResponseEntity.ok(amrId);
    }

    @DeleteMapping("/{amrId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAlmacenRepisa(
            @PathVariable(name = "amrId") final Integer amrId) {
        final ReferencedWarning referencedWarning = almacenRepisaService.getReferencedWarning(amrId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        almacenRepisaService.delete(amrId);
        return ResponseEntity.noContent().build();
    }

}
