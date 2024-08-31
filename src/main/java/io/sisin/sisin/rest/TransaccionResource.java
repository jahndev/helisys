package io.sisin.sisin.rest;

import io.sisin.sisin.domain.TrasaccionEvento;
import io.sisin.sisin.domain.Usuario;
import io.sisin.sisin.model.TransaccionDTO;
import io.sisin.sisin.repos.TrasaccionEventoRepository;
import io.sisin.sisin.repos.UsuarioRepository;
import io.sisin.sisin.service.TransaccionService;
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
@RequestMapping(value = "/api/transaccions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionResource {

    private final TransaccionService transaccionService;
    private final UsuarioRepository usuarioRepository;
    private final TrasaccionEventoRepository trasaccionEventoRepository;

    public TransaccionResource(final TransaccionService transaccionService,
            final UsuarioRepository usuarioRepository,
            final TrasaccionEventoRepository trasaccionEventoRepository) {
        this.transaccionService = transaccionService;
        this.usuarioRepository = usuarioRepository;
        this.trasaccionEventoRepository = trasaccionEventoRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> getAllTransaccions() {
        return ResponseEntity.ok(transaccionService.findAll());
    }

    @GetMapping("/{tceId}")
    public ResponseEntity<TransaccionDTO> getTransaccion(
            @PathVariable(name = "tceId") final Integer tceId) {
        return ResponseEntity.ok(transaccionService.get(tceId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTransaccion(
            @RequestBody @Valid final TransaccionDTO transaccionDTO) {
        final Integer createdTceId = transaccionService.create(transaccionDTO);
        return new ResponseEntity<>(createdTceId, HttpStatus.CREATED);
    }

    @PutMapping("/{tceId}")
    public ResponseEntity<Integer> updateTransaccion(
            @PathVariable(name = "tceId") final Integer tceId,
            @RequestBody @Valid final TransaccionDTO transaccionDTO) {
        transaccionService.update(tceId, transaccionDTO);
        return ResponseEntity.ok(tceId);
    }

    @DeleteMapping("/{tceId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaccion(
            @PathVariable(name = "tceId") final Integer tceId) {
        final ReferencedWarning referencedWarning = transaccionService.getReferencedWarning(tceId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        transaccionService.delete(tceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tceUsrValues")
    public ResponseEntity<Map<Integer, String>> getTceUsrValues() {
        return ResponseEntity.ok(usuarioRepository.findAll(Sort.by("usrId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Usuario::getUsrId, Usuario::getUsrNombre)));
    }

    @GetMapping("/tceTvoValues")
    public ResponseEntity<Map<Integer, Integer>> getTceTvoValues() {
        return ResponseEntity.ok(trasaccionEventoRepository.findAll(Sort.by("tvoId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(TrasaccionEvento::getTvoId, TrasaccionEvento::getTvoId)));
    }

}
