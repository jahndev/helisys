package io.sisin.sisin.service;

import io.sisin.sisin.domain.TrasaccionEvento;
import io.sisin.sisin.domain.TrasaccionTipoEvento;
import io.sisin.sisin.model.TrasaccionTipoEventoDTO;
import io.sisin.sisin.repos.TrasaccionEventoRepository;
import io.sisin.sisin.repos.TrasaccionTipoEventoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TrasaccionTipoEventoService {

    private final TrasaccionTipoEventoRepository trasaccionTipoEventoRepository;
    private final TrasaccionEventoRepository trasaccionEventoRepository;

    public TrasaccionTipoEventoService(
            final TrasaccionTipoEventoRepository trasaccionTipoEventoRepository,
            final TrasaccionEventoRepository trasaccionEventoRepository) {
        this.trasaccionTipoEventoRepository = trasaccionTipoEventoRepository;
        this.trasaccionEventoRepository = trasaccionEventoRepository;
    }

    public List<TrasaccionTipoEventoDTO> findAll() {
        final List<TrasaccionTipoEvento> trasaccionTipoEventoes = trasaccionTipoEventoRepository.findAll(Sort.by("tteId"));
        return trasaccionTipoEventoes.stream()
                .map(trasaccionTipoEvento -> mapToDTO(trasaccionTipoEvento, new TrasaccionTipoEventoDTO()))
                .toList();
    }

    public TrasaccionTipoEventoDTO get(final Integer tteId) {
        return trasaccionTipoEventoRepository.findById(tteId)
                .map(trasaccionTipoEvento -> mapToDTO(trasaccionTipoEvento, new TrasaccionTipoEventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TrasaccionTipoEventoDTO trasaccionTipoEventoDTO) {
        final TrasaccionTipoEvento trasaccionTipoEvento = new TrasaccionTipoEvento();
        mapToEntity(trasaccionTipoEventoDTO, trasaccionTipoEvento);
        return trasaccionTipoEventoRepository.save(trasaccionTipoEvento).getTteId();
    }

    public void update(final Integer tteId, final TrasaccionTipoEventoDTO trasaccionTipoEventoDTO) {
        final TrasaccionTipoEvento trasaccionTipoEvento = trasaccionTipoEventoRepository.findById(tteId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(trasaccionTipoEventoDTO, trasaccionTipoEvento);
        trasaccionTipoEventoRepository.save(trasaccionTipoEvento);
    }

    public void delete(final Integer tteId) {
        trasaccionTipoEventoRepository.deleteById(tteId);
    }

    private TrasaccionTipoEventoDTO mapToDTO(final TrasaccionTipoEvento trasaccionTipoEvento,
            final TrasaccionTipoEventoDTO trasaccionTipoEventoDTO) {
        trasaccionTipoEventoDTO.setTteId(trasaccionTipoEvento.getTteId());
        trasaccionTipoEventoDTO.setTteNombre(trasaccionTipoEvento.getTteNombre());
        return trasaccionTipoEventoDTO;
    }

    private TrasaccionTipoEvento mapToEntity(final TrasaccionTipoEventoDTO trasaccionTipoEventoDTO,
            final TrasaccionTipoEvento trasaccionTipoEvento) {
        trasaccionTipoEvento.setTteNombre(trasaccionTipoEventoDTO.getTteNombre());
        return trasaccionTipoEvento;
    }

    public ReferencedWarning getReferencedWarning(final Integer tteId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TrasaccionTipoEvento trasaccionTipoEvento = trasaccionTipoEventoRepository.findById(tteId)
                .orElseThrow(NotFoundException::new);
        final TrasaccionEvento tvoTteTrasaccionEvento = trasaccionEventoRepository.findFirstByTvoTte(trasaccionTipoEvento);
        if (tvoTteTrasaccionEvento != null) {
            referencedWarning.setKey("trasaccionTipoEvento.trasaccionEvento.tvoTte.referenced");
            referencedWarning.addParam(tvoTteTrasaccionEvento.getTvoId());
            return referencedWarning;
        }
        return null;
    }

}
