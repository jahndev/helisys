package io.sisin.sisin.rest;

import io.sisin.sisin.domain.Escuadron;
import io.sisin.sisin.domain.Grado;
import io.sisin.sisin.model.UsuarioDTO;
import io.sisin.sisin.repos.EscuadronRepository;
import io.sisin.sisin.repos.GradoRepository;
import io.sisin.sisin.service.UsuarioService;
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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;
    private final GradoRepository gradoRepository;
    private final EscuadronRepository escuadronRepository;

    public UsuarioResource(final UsuarioService usuarioService,
            final GradoRepository gradoRepository, final EscuadronRepository escuadronRepository) {
        this.usuarioService = usuarioService;
        this.gradoRepository = gradoRepository;
        this.escuadronRepository = escuadronRepository;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{usrId}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "usrId") final Integer usrId) {
        return ResponseEntity.ok(usuarioService.get(usrId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdUsrId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdUsrId, HttpStatus.CREATED);
    }

    @PutMapping("/{usrId}")
    public ResponseEntity<Integer> updateUsuario(@PathVariable(name = "usrId") final Integer usrId,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(usrId, usuarioDTO);
        return ResponseEntity.ok(usrId);
    }

    @DeleteMapping("/{usrId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "usrId") final Integer usrId) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(usrId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(usrId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usrGdoValues")
    public ResponseEntity<Map<Integer, String>> getUsrGdoValues() {
        return ResponseEntity.ok(gradoRepository.findAll(Sort.by("gdoId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Grado::getGdoId, Grado::getGdoNombre)));
    }

    @GetMapping("/usrEdnValues")
    public ResponseEntity<Map<Integer, String>> getUsrEdnValues() {
        return ResponseEntity.ok(escuadronRepository.findAll(Sort.by("ednId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Escuadron::getEdnId, Escuadron::getEdnNombre)));
    }

}
