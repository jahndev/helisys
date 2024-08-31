package io.sisin.sisin.rest;

import io.sisin.sisin.domain.AlmacenRepisa;
import io.sisin.sisin.domain.TipoProducto;
import io.sisin.sisin.model.ProductoDTO;
import io.sisin.sisin.repos.AlmacenRepisaRepository;
import io.sisin.sisin.repos.TipoProductoRepository;
import io.sisin.sisin.service.ProductoService;
import io.sisin.sisin.util.CustomCollectors;
import io.sisin.sisin.util.ReferencedException;
import io.sisin.sisin.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/api/productos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductoResource {

    private final ProductoService productoService;
    private final AlmacenRepisaRepository almacenRepisaRepository;
    private final TipoProductoRepository tipoProductoRepository;

    public ProductoResource(final ProductoService productoService,
            final AlmacenRepisaRepository almacenRepisaRepository,
            final TipoProductoRepository tipoProductoRepository) {
        this.productoService = productoService;
        this.almacenRepisaRepository = almacenRepisaRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{proId}")
    public ResponseEntity<ProductoDTO> getProducto(
            @PathVariable(name = "proId") final Integer proId) {
        return ResponseEntity.ok(productoService.get(proId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProducto(
            @RequestBody @Valid final ProductoDTO productoDTO) {
        final Integer createdProId = productoService.create(productoDTO);
        return new ResponseEntity<>(createdProId, HttpStatus.CREATED);
    }

    @PutMapping("/{proId}")
    public ResponseEntity<Integer> updateProducto(@PathVariable(name = "proId") final Integer proId,
            @RequestBody @Valid final ProductoDTO productoDTO) {
        productoService.update(proId, productoDTO);
        return ResponseEntity.ok(proId);
    }

    @DeleteMapping("/{proId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProducto(@PathVariable(name = "proId") final Integer proId) {
        final ReferencedWarning referencedWarning = productoService.getReferencedWarning(proId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        productoService.delete(proId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proAmrValues")
    public ResponseEntity<Map<Integer, String>> getProAmrValues() {
        return ResponseEntity.ok(almacenRepisaRepository.findAll(Sort.by("amrId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(AlmacenRepisa::getAmrId, AlmacenRepisa::getAmrDescripcion)));
    }

    @GetMapping("/proTpoIdoValues")
    public ResponseEntity<Map<Integer, String>> getProTpoIdoValues() {
        return ResponseEntity.ok(tipoProductoRepository.findAll(Sort.by("tpoIdo"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TipoProducto::getTpoIdo, TipoProducto::getTpoNombreTipo)));
    }

}
