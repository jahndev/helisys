package io.sisin.sisin.rest;

import io.sisin.sisin.model.TipoProductoDTO;
import io.sisin.sisin.service.TipoProductoService;
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
@RequestMapping(value = "/api/tipoProducto", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoProductoResource {

    private final TipoProductoService tipoProductoService;

    public TipoProductoResource(final TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoProductoDTO>> getAllTipoProducto() {
        return ResponseEntity.ok(tipoProductoService.findAll());
    }

    @GetMapping("/{tpoIdo}")
    public ResponseEntity<TipoProductoDTO> getTipoProducto(
            @PathVariable(name = "tpoIdo") final Integer tpoIdo) {
        return ResponseEntity.ok(tipoProductoService.get(tpoIdo));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTipoProducto(
            @RequestBody @Valid final TipoProductoDTO tipoProductoDTO) {
        final Integer createdTpoIdo = tipoProductoService.create(tipoProductoDTO);
        return new ResponseEntity<>(createdTpoIdo, HttpStatus.CREATED);
    }

    @PutMapping("/{tpoIdo}")
    public ResponseEntity<Integer> updateTipoProducto(
            @PathVariable(name = "tpoIdo") final Integer tpoIdo,
            @RequestBody @Valid final TipoProductoDTO tipoProductoDTO) {
        tipoProductoService.update(tpoIdo, tipoProductoDTO);
        return ResponseEntity.ok(tpoIdo);
    }

    @DeleteMapping("/{tpoIdo}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTipoProducto(
            @PathVariable(name = "tpoIdo") final Integer tpoIdo) {
        final ReferencedWarning referencedWarning = tipoProductoService.getReferencedWarning(tpoIdo);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        tipoProductoService.delete(tpoIdo);
        return ResponseEntity.noContent().build();
    }

}
