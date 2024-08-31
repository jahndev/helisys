package io.sisin.sisin.rest;

import io.sisin.sisin.model.ModeloAeronaveDTO;
import io.sisin.sisin.service.ModeloAeronaveService;
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
@RequestMapping(value = "/api/modeloAeronaves", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModeloAeronaveResource {

    private final ModeloAeronaveService modeloAeronaveService;

    public ModeloAeronaveResource(final ModeloAeronaveService modeloAeronaveService) {
        this.modeloAeronaveService = modeloAeronaveService;
    }

    @GetMapping
    public ResponseEntity<List<ModeloAeronaveDTO>> getAllModeloAeronaves() {
        return ResponseEntity.ok(modeloAeronaveService.findAll());
    }

    @GetMapping("/{mre}")
    public ResponseEntity<ModeloAeronaveDTO> getModeloAeronave(
            @PathVariable(name = "mre") final Integer mre) {
        return ResponseEntity.ok(modeloAeronaveService.get(mre));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createModeloAeronave(
            @RequestBody @Valid final ModeloAeronaveDTO modeloAeronaveDTO) {
        final Integer createdMre = modeloAeronaveService.create(modeloAeronaveDTO);
        return new ResponseEntity<>(createdMre, HttpStatus.CREATED);
    }

    @PutMapping("/{mre}")
    public ResponseEntity<Integer> updateModeloAeronave(
            @PathVariable(name = "mre") final Integer mre,
            @RequestBody @Valid final ModeloAeronaveDTO modeloAeronaveDTO) {
        modeloAeronaveService.update(mre, modeloAeronaveDTO);
        return ResponseEntity.ok(mre);
    }

    @DeleteMapping("/{mre}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteModeloAeronave(
            @PathVariable(name = "mre") final Integer mre) {
        modeloAeronaveService.delete(mre);
        return ResponseEntity.noContent().build();
    }

}
