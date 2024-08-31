package io.sisin.sisin.service;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.Transaccion;
import io.sisin.sisin.domain.TrasaccionEvento;
import io.sisin.sisin.domain.TrasaccionTipoEvento;
import io.sisin.sisin.model.TrasaccionEventoDTO;
import io.sisin.sisin.repos.AeronaveRepository;
import io.sisin.sisin.repos.TransaccionRepository;
import io.sisin.sisin.repos.TrasaccionEventoRepository;
import io.sisin.sisin.repos.TrasaccionTipoEventoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TrasaccionEventoService {

    private final TrasaccionEventoRepository trasaccionEventoRepository;
    private final TrasaccionTipoEventoRepository trasaccionTipoEventoRepository;
    private final AeronaveRepository aeronaveRepository;
    private final TransaccionRepository transaccionRepository;

    public TrasaccionEventoService(final TrasaccionEventoRepository trasaccionEventoRepository,
            final TrasaccionTipoEventoRepository trasaccionTipoEventoRepository,
            final AeronaveRepository aeronaveRepository,
            final TransaccionRepository transaccionRepository) {
        this.trasaccionEventoRepository = trasaccionEventoRepository;
        this.trasaccionTipoEventoRepository = trasaccionTipoEventoRepository;
        this.aeronaveRepository = aeronaveRepository;
        this.transaccionRepository = transaccionRepository;
    }

    public List<TrasaccionEventoDTO> findAll() {
        final List<TrasaccionEvento> trasaccionEventoes = trasaccionEventoRepository.findAll(Sort.by("tvoId"));
        return trasaccionEventoes.stream()
                .map(trasaccionEvento -> mapToDTO(trasaccionEvento, new TrasaccionEventoDTO()))
                .toList();
    }

    public TrasaccionEventoDTO get(final Integer tvoId) {
        return trasaccionEventoRepository.findById(tvoId)
                .map(trasaccionEvento -> mapToDTO(trasaccionEvento, new TrasaccionEventoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TrasaccionEventoDTO trasaccionEventoDTO) {
        final TrasaccionEvento trasaccionEvento = new TrasaccionEvento();
        mapToEntity(trasaccionEventoDTO, trasaccionEvento);
        return trasaccionEventoRepository.save(trasaccionEvento).getTvoId();
    }

    public void update(final Integer tvoId, final TrasaccionEventoDTO trasaccionEventoDTO) {
        final TrasaccionEvento trasaccionEvento = trasaccionEventoRepository.findById(tvoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(trasaccionEventoDTO, trasaccionEvento);
        trasaccionEventoRepository.save(trasaccionEvento);
    }

    public void delete(final Integer tvoId) {
        trasaccionEventoRepository.deleteById(tvoId);
    }

    private TrasaccionEventoDTO mapToDTO(final TrasaccionEvento trasaccionEvento,
            final TrasaccionEventoDTO trasaccionEventoDTO) {
        trasaccionEventoDTO.setTvoId(trasaccionEvento.getTvoId());
        trasaccionEventoDTO.setTvoFecha(trasaccionEvento.getTvoFecha());
        trasaccionEventoDTO.setTvoTte(trasaccionEvento.getTvoTte() == null ? null : trasaccionEvento.getTvoTte().getTteId());
        trasaccionEventoDTO.setAeronavesAnv(trasaccionEvento.getAeronavesAnv() == null ? null : trasaccionEvento.getAeronavesAnv().getAnvId());
        return trasaccionEventoDTO;
    }

    private TrasaccionEvento mapToEntity(final TrasaccionEventoDTO trasaccionEventoDTO,
            final TrasaccionEvento trasaccionEvento) {
        trasaccionEvento.setTvoFecha(trasaccionEventoDTO.getTvoFecha());
        final TrasaccionTipoEvento tvoTte = trasaccionEventoDTO.getTvoTte() == null ? null : trasaccionTipoEventoRepository.findById(trasaccionEventoDTO.getTvoTte())
                .orElseThrow(() -> new NotFoundException("tvoTte not found"));
        trasaccionEvento.setTvoTte(tvoTte);
        final Aeronave aeronavesAnv = trasaccionEventoDTO.getAeronavesAnv() == null ? null : aeronaveRepository.findById(trasaccionEventoDTO.getAeronavesAnv())
                .orElseThrow(() -> new NotFoundException("aeronavesAnv not found"));
        trasaccionEvento.setAeronavesAnv(aeronavesAnv);
        return trasaccionEvento;
    }

    public ReferencedWarning getReferencedWarning(final Integer tvoId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TrasaccionEvento trasaccionEvento = trasaccionEventoRepository.findById(tvoId)
                .orElseThrow(NotFoundException::new);
        final Transaccion tceTvoTransaccion = transaccionRepository.findFirstByTceTvo(trasaccionEvento);
        if (tceTvoTransaccion != null) {
            referencedWarning.setKey("trasaccionEvento.transaccion.tceTvo.referenced");
            referencedWarning.addParam(tceTvoTransaccion.getTceId());
            return referencedWarning;
        }
        return null;
    }

}
