package io.sisin.sisin.service;

import io.sisin.sisin.domain.Grado;
import io.sisin.sisin.domain.Usuario;
import io.sisin.sisin.model.GradoDTO;
import io.sisin.sisin.repos.GradoRepository;
import io.sisin.sisin.repos.UsuarioRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GradoService {

    private final GradoRepository gradoRepository;
    private final UsuarioRepository usuarioRepository;

    public GradoService(final GradoRepository gradoRepository,
            final UsuarioRepository usuarioRepository) {
        this.gradoRepository = gradoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<GradoDTO> findAll() {
        final List<Grado> gradoes = gradoRepository.findAll(Sort.by("gdoId"));
        return gradoes.stream()
                .map(grado -> mapToDTO(grado, new GradoDTO()))
                .toList();
    }

    public GradoDTO get(final Integer gdoId) {
        return gradoRepository.findById(gdoId)
                .map(grado -> mapToDTO(grado, new GradoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final GradoDTO gradoDTO) {
        final Grado grado = new Grado();
        mapToEntity(gradoDTO, grado);
        return gradoRepository.save(grado).getGdoId();
    }

    public void update(final Integer gdoId, final GradoDTO gradoDTO) {
        final Grado grado = gradoRepository.findById(gdoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(gradoDTO, grado);
        gradoRepository.save(grado);
    }

    public void delete(final Integer gdoId) {
        gradoRepository.deleteById(gdoId);
    }

    private GradoDTO mapToDTO(final Grado grado, final GradoDTO gradoDTO) {
        gradoDTO.setGdoId(grado.getGdoId());
        gradoDTO.setGdoNombre(grado.getGdoNombre());
        return gradoDTO;
    }

    private Grado mapToEntity(final GradoDTO gradoDTO, final Grado grado) {
        grado.setGdoNombre(gradoDTO.getGdoNombre());
        return grado;
    }

    public boolean gdoNombreExists(final String gdoNombre) {
        return gradoRepository.existsByGdoNombreIgnoreCase(gdoNombre);
    }

    public ReferencedWarning getReferencedWarning(final Integer gdoId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Grado grado = gradoRepository.findById(gdoId)
                .orElseThrow(NotFoundException::new);
        final Usuario usrGdoUsuario = usuarioRepository.findFirstByUsrGdo(grado);
        if (usrGdoUsuario != null) {
            referencedWarning.setKey("grado.usuario.usrGdo.referenced");
            referencedWarning.addParam(usrGdoUsuario.getUsrId());
            return referencedWarning;
        }
        return null;
    }

}
