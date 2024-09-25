package io.sisin.sisin.service;

import io.sisin.sisin.domain.TransaccionEvento;
import io.sisin.sisin.domain.TransaccionTipoEvento;
import io.sisin.sisin.model.TransaccionTipoEventoDTO;
import io.sisin.sisin.repos.TransaccionEventoRepository;
import io.sisin.sisin.repos.TransaccionTipoEventoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransaccionTipoEventoService {

    private final TransaccionTipoEventoRepository transaccionTipoEventoRepository;
    private final TransaccionEventoRepository transaccionEventoRepository;

    public TransaccionTipoEventoService(
            final TransaccionTipoEventoRepository transaccionTipoEventoRepository,
            final TransaccionEventoRepository transaccionEventoRepository) {
        this.transaccionTipoEventoRepository = transaccionTipoEventoRepository;
        this.transaccionEventoRepository = transaccionEventoRepository;
    }

    public List<TransaccionTipoEventoDTO> findAll() {
        final List<TransaccionTipoEvento> transaccionTipoEventos = transaccionTipoEventoRepository.findAll(Sort.by("tteId"));
        return transaccionTipoEventos.stream()
                .map(transaccionTipoEvento -> mapToDTO(transaccionTipoEvento, new TransaccionTipoEventoDTO()))
                .toList();
    }

    public TransaccionTipoEventoDTO get(final Integer tteId) {
        return transaccionTipoEventoRepository.findById(tteId)
                .map(transaccionTipoEvento -> mapToDTO(transaccionTipoEvento, new TransaccionTipoEventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TransaccionTipoEventoDTO transaccionTipoEventoDTO) {
        final TransaccionTipoEvento transaccionTipoEvento = new TransaccionTipoEvento();
        mapToEntity(transaccionTipoEventoDTO, transaccionTipoEvento);
        return transaccionTipoEventoRepository.save(transaccionTipoEvento).getTteId();
    }

    public void update(final Integer tteId, final TransaccionTipoEventoDTO transaccionTipoEventoDTO) {
        final TransaccionTipoEvento transaccionTipoEvento = transaccionTipoEventoRepository.findById(tteId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transaccionTipoEventoDTO, transaccionTipoEvento);
        transaccionTipoEventoRepository.save(transaccionTipoEvento);
    }

    public void delete(final Integer tteId) {
        transaccionTipoEventoRepository.deleteById(tteId);
    }

    private TransaccionTipoEventoDTO mapToDTO(final TransaccionTipoEvento transaccionTipoEvento,
                                              final TransaccionTipoEventoDTO transaccionTipoEventoDTO) {
        transaccionTipoEventoDTO.setTteId(transaccionTipoEvento.getTteId());
        transaccionTipoEventoDTO.setTteNombre(transaccionTipoEvento.getTteNombre());
        return transaccionTipoEventoDTO;
    }

    private TransaccionTipoEvento mapToEntity(final TransaccionTipoEventoDTO transaccionTipoEventoDTO,
                                              final TransaccionTipoEvento transaccionTipoEvento) {
        transaccionTipoEvento.setTteNombre(transaccionTipoEventoDTO.getTteNombre());
        return transaccionTipoEvento;
    }

    public ReferencedWarning getReferencedWarning(final Integer tteId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TransaccionTipoEvento transaccionTipoEvento = transaccionTipoEventoRepository.findById(tteId)
                .orElseThrow(NotFoundException::new);
        final TransaccionEvento tvoTteTransaccionEvento = transaccionEventoRepository.findFirstByTvoTte(transaccionTipoEvento);
        if (tvoTteTransaccionEvento != null) {
            referencedWarning.setKey("transaccionTipoEvento.transaccionEvento.tvoTte.referenced");
            referencedWarning.addParam(tvoTteTransaccionEvento.getTvoId());
            return referencedWarning;
        }
        return null;
    }

}
