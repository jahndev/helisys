package io.sisin.sisin.rest;

import io.sisin.sisin.model.TransaccionesProductoDTO;
import io.sisin.sisin.service.TransaccionesProductoService;
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
@RequestMapping(value = "/api/transaccionesProductos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionesProductoResource {

    private final TransaccionesProductoService transaccionesProductoService;

    public TransaccionesProductoResource(
            final TransaccionesProductoService transaccionesProductoService) {
        this.transaccionesProductoService = transaccionesProductoService;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionesProductoDTO>> getAllTransaccionesProductos() {
        return ResponseEntity.ok(transaccionesProductoService.findAll());
    }

    @GetMapping("/{tcoId}")
    public ResponseEntity<TransaccionesProductoDTO> getTransaccionesProducto(
            @PathVariable(name = "tcoId") final Integer tcoId) {
        return ResponseEntity.ok(transaccionesProductoService.get(tcoId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTransaccionesProducto(
            @RequestBody @Valid final TransaccionesProductoDTO transaccionesProductoDTO) {
        final Integer createdTcoId = transaccionesProductoService.create(transaccionesProductoDTO);
        return new ResponseEntity<>(createdTcoId, HttpStatus.CREATED);
    }

    @PutMapping("/{tcoId}")
    public ResponseEntity<Integer> updateTransaccionesProducto(
            @PathVariable(name = "tcoId") final Integer tcoId,
            @RequestBody @Valid final TransaccionesProductoDTO transaccionesProductoDTO) {
        transaccionesProductoService.update(tcoId, transaccionesProductoDTO);
        return ResponseEntity.ok(tcoId);
    }

    @DeleteMapping("/{tcoId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaccionesProducto(
            @PathVariable(name = "tcoId") final Integer tcoId) {
        transaccionesProductoService.delete(tcoId);
        return ResponseEntity.noContent().build();
    }

}
