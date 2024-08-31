package io.sisin.sisin.service;

import io.sisin.sisin.domain.Escuadron;
import io.sisin.sisin.domain.Unidad;
import io.sisin.sisin.domain.Usuario;
import io.sisin.sisin.model.EscuadronDTO;
import io.sisin.sisin.repos.EscuadronRepository;
import io.sisin.sisin.repos.UnidadRepository;
import io.sisin.sisin.repos.UsuarioRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EscuadronService {

    private final EscuadronRepository escuadronRepository;
    private final UnidadRepository unidadRepository;
    private final UsuarioRepository usuarioRepository;

    public EscuadronService(final EscuadronRepository escuadronRepository,
            final UnidadRepository unidadRepository, final UsuarioRepository usuarioRepository) {
        this.escuadronRepository = escuadronRepository;
        this.unidadRepository = unidadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<EscuadronDTO> findAll() {
        final List<Escuadron> escuadrons = escuadronRepository.findAll(Sort.by("ednId"));
        return escuadrons.stream()
                .map(escuadron -> mapToDTO(escuadron, new EscuadronDTO()))
                .toList();
    }

    public EscuadronDTO get(final Integer ednId) {
        return escuadronRepository.findById(ednId)
                .map(escuadron -> mapToDTO(escuadron, new EscuadronDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final EscuadronDTO escuadronDTO) {
        final Escuadron escuadron = new Escuadron();
        mapToEntity(escuadronDTO, escuadron);
        return escuadronRepository.save(escuadron).getEdnId();
    }

    public void update(final Integer ednId, final EscuadronDTO escuadronDTO) {
        final Escuadron escuadron = escuadronRepository.findById(ednId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(escuadronDTO, escuadron);
        escuadronRepository.save(escuadron);
    }

    public void delete(final Integer ednId) {
        escuadronRepository.deleteById(ednId);
    }

    private EscuadronDTO mapToDTO(final Escuadron escuadron, final EscuadronDTO escuadronDTO) {
        escuadronDTO.setEdnId(escuadron.getEdnId());
        escuadronDTO.setEdnNombre(escuadron.getEdnNombre());
        escuadronDTO.setEdnUnd(escuadron.getEdnUnd() == null ? null : escuadron.getEdnUnd().getUndId());
        return escuadronDTO;
    }

    private Escuadron mapToEntity(final EscuadronDTO escuadronDTO, final Escuadron escuadron) {
        escuadron.setEdnNombre(escuadronDTO.getEdnNombre());
        final Unidad ednUnd = escuadronDTO.getEdnUnd() == null ? null : unidadRepository.findById(escuadronDTO.getEdnUnd())
                .orElseThrow(() -> new NotFoundException("ednUnd not found"));
        escuadron.setEdnUnd(ednUnd);
        return escuadron;
    }

    public ReferencedWarning getReferencedWarning(final Integer ednId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Escuadron escuadron = escuadronRepository.findById(ednId)
                .orElseThrow(NotFoundException::new);
        final Usuario usrEdnUsuario = usuarioRepository.findFirstByUsrEdn(escuadron);
        if (usrEdnUsuario != null) {
            referencedWarning.setKey("escuadron.usuario.usrEdn.referenced");
            referencedWarning.addParam(usrEdnUsuario.getUsrId());
            return referencedWarning;
        }
        return null;
    }

}
