package io.sisin.sisin.service;

import io.sisin.sisin.domain.Brigada;
import io.sisin.sisin.domain.Escuadron;
import io.sisin.sisin.domain.Unidad;
import io.sisin.sisin.model.UnidadDTO;
import io.sisin.sisin.repos.BrigadaRepository;
import io.sisin.sisin.repos.EscuadronRepository;
import io.sisin.sisin.repos.UnidadRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UnidadService {

    private final UnidadRepository unidadRepository;
    private final BrigadaRepository brigadaRepository;
    private final EscuadronRepository escuadronRepository;

    public UnidadService(final UnidadRepository unidadRepository,
            final BrigadaRepository brigadaRepository,
            final EscuadronRepository escuadronRepository) {
        this.unidadRepository = unidadRepository;
        this.brigadaRepository = brigadaRepository;
        this.escuadronRepository = escuadronRepository;
    }

    public List<UnidadDTO> findAll() {
        final List<Unidad> unidads = unidadRepository.findAll(Sort.by("undId"));
        return unidads.stream()
                .map(unidad -> mapToDTO(unidad, new UnidadDTO()))
                .toList();
    }

    public UnidadDTO get(final Integer undId) {
        return unidadRepository.findById(undId)
                .map(unidad -> mapToDTO(unidad, new UnidadDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UnidadDTO unidadDTO) {
        final Unidad unidad = new Unidad();
        mapToEntity(unidadDTO, unidad);
        return unidadRepository.save(unidad).getUndId();
    }

    public void update(final Integer undId, final UnidadDTO unidadDTO) {
        final Unidad unidad = unidadRepository.findById(undId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(unidadDTO, unidad);
        unidadRepository.save(unidad);
    }

    public void delete(final Integer undId) {
        unidadRepository.deleteById(undId);
    }

    private UnidadDTO mapToDTO(final Unidad unidad, final UnidadDTO unidadDTO) {
        unidadDTO.setUndId(unidad.getUndId());
        unidadDTO.setUndNombre(unidad.getUndNombre());
        unidadDTO.setUndTelefono(unidad.getUndTelefono());
        unidadDTO.setUndFax(unidad.getUndFax());
        unidadDTO.setUndComandanteNombre(unidad.getUndComandanteNombre());
        unidadDTO.setUndDireccion(unidad.getUndDireccion());
        unidadDTO.setUndDepartamento(unidad.getUndDepartamento());
        unidadDTO.setUndProvincia(unidad.getUndProvincia());
        unidadDTO.setUndCiudad(unidad.getUndCiudad());
        unidadDTO.setUndBga(unidad.getUndBga() == null ? null : unidad.getUndBga().getBgaId());
        return unidadDTO;
    }

    private Unidad mapToEntity(final UnidadDTO unidadDTO, final Unidad unidad) {
        unidad.setUndNombre(unidadDTO.getUndNombre());
        unidad.setUndTelefono(unidadDTO.getUndTelefono());
        unidad.setUndFax(unidadDTO.getUndFax());
        unidad.setUndComandanteNombre(unidadDTO.getUndComandanteNombre());
        unidad.setUndDireccion(unidadDTO.getUndDireccion());
        unidad.setUndDepartamento(unidadDTO.getUndDepartamento());
        unidad.setUndProvincia(unidadDTO.getUndProvincia());
        unidad.setUndCiudad(unidadDTO.getUndCiudad());
        final Brigada undBga = unidadDTO.getUndBga() == null ? null : brigadaRepository.findById(unidadDTO.getUndBga())
                .orElseThrow(() -> new NotFoundException("undBga not found"));
        unidad.setUndBga(undBga);
        return unidad;
    }

    public ReferencedWarning getReferencedWarning(final Integer undId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Unidad unidad = unidadRepository.findById(undId)
                .orElseThrow(NotFoundException::new);
        final Escuadron ednUndEscuadron = escuadronRepository.findFirstByEdnUnd(unidad);
        if (ednUndEscuadron != null) {
            referencedWarning.setKey("unidad.escuadron.ednUnd.referenced");
            referencedWarning.addParam(ednUndEscuadron.getEdnId());
            return referencedWarning;
        }
        return null;
    }

}
