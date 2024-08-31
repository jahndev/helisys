package io.sisin.sisin.rest;

import io.sisin.sisin.model.BrigadaDTO;
import io.sisin.sisin.service.BrigadaService;
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
@RequestMapping(value = "/api/brigadas", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrigadaResource {

    private final BrigadaService brigadaService;

    public BrigadaResource(final BrigadaService brigadaService) {
        this.brigadaService = brigadaService;
    }

    @GetMapping
    public ResponseEntity<List<BrigadaDTO>> getAllBrigadas() {
        return ResponseEntity.ok(brigadaService.findAll());
    }

    @GetMapping("/{bgaId}")
    public ResponseEntity<BrigadaDTO> getBrigada(
            @PathVariable(name = "bgaId") final Integer bgaId) {
        return ResponseEntity.ok(brigadaService.get(bgaId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBrigada(@RequestBody @Valid final BrigadaDTO brigadaDTO) {
        final Integer createdBgaId = brigadaService.create(brigadaDTO);
        return new ResponseEntity<>(createdBgaId, HttpStatus.CREATED);
    }

    @PutMapping("/{bgaId}")
    public ResponseEntity<Integer> updateBrigada(@PathVariable(name = "bgaId") final Integer bgaId,
            @RequestBody @Valid final BrigadaDTO brigadaDTO) {
        brigadaService.update(bgaId, brigadaDTO);
        return ResponseEntity.ok(bgaId);
    }

    @DeleteMapping("/{bgaId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBrigada(@PathVariable(name = "bgaId") final Integer bgaId) {
        final ReferencedWarning referencedWarning = brigadaService.getReferencedWarning(bgaId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        brigadaService.delete(bgaId);
        return ResponseEntity.noContent().build();
    }

}
