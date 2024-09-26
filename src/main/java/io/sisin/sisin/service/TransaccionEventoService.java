package io.sisin.sisin.service;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.Transaccion;
import io.sisin.sisin.domain.TransaccionEvento;
import io.sisin.sisin.domain.TransaccionTipoEvento;
import io.sisin.sisin.model.TransaccionEventoDTO;
import io.sisin.sisin.repos.AeronaveRepository;
import io.sisin.sisin.repos.TransaccionRepository;
import io.sisin.sisin.repos.TransaccionEventoRepository;
import io.sisin.sisin.repos.TransaccionTipoEventoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransaccionEventoService {

    private final TransaccionEventoRepository transaccionEventoRepository;
    private final TransaccionTipoEventoRepository transaccionTipoEventoRepository;
    private final AeronaveRepository aeronaveRepository;
    private final TransaccionRepository transaccionRepository;

    public TransaccionEventoService(final TransaccionEventoRepository transaccionEventoRepository,
                                    final TransaccionTipoEventoRepository transaccionTipoEventoRepository,
                                    final AeronaveRepository aeronaveRepository,
                                    final TransaccionRepository transaccionRepository) {
        this.transaccionEventoRepository = transaccionEventoRepository;
        this.transaccionTipoEventoRepository = transaccionTipoEventoRepository;
        this.aeronaveRepository = aeronaveRepository;
        this.transaccionRepository = transaccionRepository;
    }

    public List<TransaccionEventoDTO> findAll() {
        final List<TransaccionEvento> transaccionEventos = transaccionEventoRepository.findAll(Sort.by("tvoId"));
        return transaccionEventos.stream()
                .map(transaccionEvento -> mapToDTO(transaccionEvento, new TransaccionEventoDTO()))
                .toList();
    }

    public TransaccionEventoDTO get(final Integer tvoId) {
        return transaccionEventoRepository.findById(tvoId)
                .map(transaccionEvento -> mapToDTO(transaccionEvento, new TransaccionEventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TransaccionEventoDTO transaccionEventoDTO) {
        final TransaccionEvento transaccionEvento = new TransaccionEvento();
        mapToEntity(transaccionEventoDTO, transaccionEvento);
        return transaccionEventoRepository.save(transaccionEvento).getTvoId();
    }

    public void update(final Integer tvoId, final TransaccionEventoDTO transaccionEventoDTO) {
        final TransaccionEvento transaccionEvento = transaccionEventoRepository.findById(tvoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transaccionEventoDTO, transaccionEvento);
        transaccionEventoRepository.save(transaccionEvento);
    }

    public void delete(final Integer tvoId) {
        transaccionEventoRepository.deleteById(tvoId);
    }

    private TransaccionEventoDTO mapToDTO(final TransaccionEvento transaccionEvento,
            final TransaccionEventoDTO transaccionEventoDTO) {
        transaccionEventoDTO.setTvoId(transaccionEvento.getTvoId());
        transaccionEventoDTO.setTvoFecha(transaccionEvento.getTvoFecha());
        transaccionEventoDTO.setTvoTte(transaccionEvento.getTvoTte() == null ? null : transaccionEvento.getTvoTte().getTteId());
        transaccionEventoDTO.setAeronavesAnv(transaccionEvento.getAeronavesAnv() == null ? null : transaccionEvento.getAeronavesAnv().getAnvId());
        return transaccionEventoDTO;
    }

    private TransaccionEvento mapToEntity(final TransaccionEventoDTO transaccionEventoDTO,
            final TransaccionEvento transaccionEvento) {
        transaccionEvento.setTvoFecha(transaccionEventoDTO.getTvoFecha());
        final TransaccionTipoEvento tvoTte = transaccionEventoDTO.getTvoTte() == null ? null : transaccionTipoEventoRepository.findById(transaccionEventoDTO.getTvoTte())
                .orElseThrow(() -> new NotFoundException("tvoTte not found"));
        transaccionEvento.setTvoTte(tvoTte);
        final Aeronave aeronavesAnv = transaccionEventoDTO.getAeronavesAnv() == null ? null : aeronaveRepository.findById(transaccionEventoDTO.getAeronavesAnv())
                .orElseThrow(() -> new NotFoundException("aeronavesAnv not found"));
        transaccionEvento.setAeronavesAnv(aeronavesAnv);
        return transaccionEvento;
    }

    public ReferencedWarning getReferencedWarning(final Integer tvoId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TransaccionEvento transaccionEvento = transaccionEventoRepository.findById(tvoId)
                .orElseThrow(NotFoundException::new);
        final Transaccion tceTvoTransaccion = transaccionRepository.findFirstByTceTvo(transaccionEvento);
        if (tceTvoTransaccion != null) {
            referencedWarning.setKey("transaccionEvento.transaccion.tceTvo.referenced");
            referencedWarning.addParam(tceTvoTransaccion.getTceId());
            return referencedWarning;
        }
        return null;
    }

}
