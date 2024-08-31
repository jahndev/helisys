package io.sisin.sisin.rest;

import io.sisin.sisin.domain.Proveedor;
import io.sisin.sisin.model.ContactoProveedorDTO;
import io.sisin.sisin.repos.ProveedorRepository;
import io.sisin.sisin.service.ContactoProveedorService;
import io.sisin.sisin.util.CustomCollectors;
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
@RequestMapping(value = "/api/contactoProveedors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactoProveedorResource {

    private final ContactoProveedorService contactoProveedorService;
    private final ProveedorRepository proveedorRepository;

    public ContactoProveedorResource(final ContactoProveedorService contactoProveedorService,
            final ProveedorRepository proveedorRepository) {
        this.contactoProveedorService = contactoProveedorService;
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    public ResponseEntity<List<ContactoProveedorDTO>> getAllContactoProveedors() {
        return ResponseEntity.ok(contactoProveedorService.findAll());
    }

    @GetMapping("/{cveId}")
    public ResponseEntity<ContactoProveedorDTO> getContactoProveedor(
            @PathVariable(name = "cveId") final Integer cveId) {
        return ResponseEntity.ok(contactoProveedorService.get(cveId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createContactoProveedor(
            @RequestBody @Valid final ContactoProveedorDTO contactoProveedorDTO) {
        final Integer createdCveId = contactoProveedorService.create(contactoProveedorDTO);
        return new ResponseEntity<>(createdCveId, HttpStatus.CREATED);
    }

    @PutMapping("/{cveId}")
    public ResponseEntity<Integer> updateContactoProveedor(
            @PathVariable(name = "cveId") final Integer cveId,
            @RequestBody @Valid final ContactoProveedorDTO contactoProveedorDTO) {
        contactoProveedorService.update(cveId, contactoProveedorDTO);
        return ResponseEntity.ok(cveId);
    }

    @DeleteMapping("/{cveId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteContactoProveedor(
            @PathVariable(name = "cveId") final Integer cveId) {
        contactoProveedorService.delete(cveId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cpePveValues")
    public ResponseEntity<Map<Integer, String>> getCpePveValues() {
        return ResponseEntity.ok(proveedorRepository.findAll(Sort.by("pveId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Proveedor::getPveId, Proveedor::getPveNombre)));
    }

}
