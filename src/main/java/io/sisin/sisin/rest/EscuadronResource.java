package io.sisin.sisin.rest;

import io.sisin.sisin.model.EscuadronDTO;
import io.sisin.sisin.service.EscuadronService;
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
@RequestMapping(value = "/api/escuadrons", produces = MediaType.APPLICATION_JSON_VALUE)
public class EscuadronResource {

    private final EscuadronService escuadronService;

    public EscuadronResource(final EscuadronService escuadronService) {
        this.escuadronService = escuadronService;
    }

    @GetMapping
    public ResponseEntity<List<EscuadronDTO>> getAllEscuadrons() {
        return ResponseEntity.ok(escuadronService.findAll());
    }

    @GetMapping("/{ednId}")
    public ResponseEntity<EscuadronDTO> getEscuadron(
            @PathVariable(name = "ednId") final Integer ednId) {
        return ResponseEntity.ok(escuadronService.get(ednId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createEscuadron(
            @RequestBody @Valid final EscuadronDTO escuadronDTO) {
        final Integer createdEdnId = escuadronService.create(escuadronDTO);
        return new ResponseEntity<>(createdEdnId, HttpStatus.CREATED);
    }

    @PutMapping("/{ednId}")
    public ResponseEntity<Integer> updateEscuadron(
            @PathVariable(name = "ednId") final Integer ednId,
            @RequestBody @Valid final EscuadronDTO escuadronDTO) {
        escuadronService.update(ednId, escuadronDTO);
        return ResponseEntity.ok(ednId);
    }

    @DeleteMapping("/{ednId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEscuadron(@PathVariable(name = "ednId") final Integer ednId) {
        final ReferencedWarning referencedWarning = escuadronService.getReferencedWarning(ednId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        escuadronService.delete(ednId);
        return ResponseEntity.noContent().build();
    }

}
