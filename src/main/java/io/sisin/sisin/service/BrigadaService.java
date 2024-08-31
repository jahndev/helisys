package io.sisin.sisin.service;

import io.sisin.sisin.domain.Brigada;
import io.sisin.sisin.domain.Unidad;
import io.sisin.sisin.model.BrigadaDTO;
import io.sisin.sisin.repos.BrigadaRepository;
import io.sisin.sisin.repos.UnidadRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BrigadaService {

    private final BrigadaRepository brigadaRepository;
    private final UnidadRepository unidadRepository;

    public BrigadaService(final BrigadaRepository brigadaRepository,
            final UnidadRepository unidadRepository) {
        this.brigadaRepository = brigadaRepository;
        this.unidadRepository = unidadRepository;
    }

    public List<BrigadaDTO> findAll() {
        final List<Brigada> brigadas = brigadaRepository.findAll(Sort.by("bgaId"));
        return brigadas.stream()
                .map(brigada -> mapToDTO(brigada, new BrigadaDTO()))
                .toList();
    }

    public BrigadaDTO get(final Integer bgaId) {
        return brigadaRepository.findById(bgaId)
                .map(brigada -> mapToDTO(brigada, new BrigadaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BrigadaDTO brigadaDTO) {
        final Brigada brigada = new Brigada();
        mapToEntity(brigadaDTO, brigada);
        return brigadaRepository.save(brigada).getBgaId();
    }

    public void update(final Integer bgaId, final BrigadaDTO brigadaDTO) {
        final Brigada brigada = brigadaRepository.findById(bgaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(brigadaDTO, brigada);
        brigadaRepository.save(brigada);
    }

    public void delete(final Integer bgaId) {
        brigadaRepository.deleteById(bgaId);
    }

    private BrigadaDTO mapToDTO(final Brigada brigada, final BrigadaDTO brigadaDTO) {
        brigadaDTO.setBgaId(brigada.getBgaId());
        brigadaDTO.setBgaNombre(brigada.getBgaNombre());
        return brigadaDTO;
    }

    private Brigada mapToEntity(final BrigadaDTO brigadaDTO, final Brigada brigada) {
        brigada.setBgaNombre(brigadaDTO.getBgaNombre());
        return brigada;
    }

    public ReferencedWarning getReferencedWarning(final Integer bgaId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Brigada brigada = brigadaRepository.findById(bgaId)
                .orElseThrow(NotFoundException::new);
        final Unidad undBgaUnidad = unidadRepository.findFirstByUndBga(brigada);
        if (undBgaUnidad != null) {
            referencedWarning.setKey("brigada.unidad.undBga.referenced");
            referencedWarning.addParam(undBgaUnidad.getUndId());
            return referencedWarning;
        }
        return null;
    }

}
