package io.sisin.sisin.rest;

import io.sisin.sisin.model.GradoDTO;
import io.sisin.sisin.service.GradoService;
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
@RequestMapping(value = "/api/grados", produces = MediaType.APPLICATION_JSON_VALUE)
public class GradoResource {

    private final GradoService gradoService;

    public GradoResource(final GradoService gradoService) {
        this.gradoService = gradoService;
    }

    @GetMapping
    public ResponseEntity<List<GradoDTO>> getAllGrados() {
        return ResponseEntity.ok(gradoService.findAll());
    }

    @GetMapping("/{gdoId}")
    public ResponseEntity<GradoDTO> getGrado(@PathVariable(name = "gdoId") final Integer gdoId) {
        return ResponseEntity.ok(gradoService.get(gdoId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createGrado(@RequestBody @Valid final GradoDTO gradoDTO) {
        final Integer createdGdoId = gradoService.create(gradoDTO);
        return new ResponseEntity<>(createdGdoId, HttpStatus.CREATED);
    }

    @PutMapping("/{gdoId}")
    public ResponseEntity<Integer> updateGrado(@PathVariable(name = "gdoId") final Integer gdoId,
            @RequestBody @Valid final GradoDTO gradoDTO) {
        gradoService.update(gdoId, gradoDTO);
        return ResponseEntity.ok(gdoId);
    }

    @DeleteMapping("/{gdoId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGrado(@PathVariable(name = "gdoId") final Integer gdoId) {
        final ReferencedWarning referencedWarning = gradoService.getReferencedWarning(gdoId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        gradoService.delete(gdoId);
        return ResponseEntity.noContent().build();
    }

}
