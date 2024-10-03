package io.sisin.sisin.service;

import io.sisin.sisin.domain.Transaccion;
import io.sisin.sisin.domain.TransaccionesProducto;
import io.sisin.sisin.domain.TransaccionEvento;
import io.sisin.sisin.domain.Usuario;
import io.sisin.sisin.model.TransaccionDTO;
import io.sisin.sisin.repos.TransaccionRepository;
import io.sisin.sisin.repos.TransaccionesProductoRepository;
import io.sisin.sisin.repos.TransaccionEventoRepository;
import io.sisin.sisin.repos.UsuarioRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final TransaccionEventoRepository transaccionEventoRepository;
    private final TransaccionesProductoRepository transaccionesProductoRepository;

    public TransaccionService(final TransaccionRepository transaccionRepository,
            final UsuarioRepository usuarioRepository,
            final TransaccionEventoRepository transaccionEventoRepository,
            final TransaccionesProductoRepository transaccionesProductoRepository) {
        this.transaccionRepository = transaccionRepository;
        this.usuarioRepository = usuarioRepository;
        this.transaccionEventoRepository = transaccionEventoRepository;
        this.transaccionesProductoRepository = transaccionesProductoRepository;
    }

    public List<TransaccionDTO> findAll() {
        final List<Transaccion> transacciones = transaccionRepository.findAll(Sort.by("tceId"));
        return transacciones.stream()
                .map(transaccion -> mapToDTO(transaccion, new TransaccionDTO()))
                .toList();
    }

    public TransaccionDTO get(final Integer tceId) {
        return transaccionRepository.findById(tceId)
                .map(transaccion -> mapToDTO(transaccion, new TransaccionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TransaccionDTO transaccionDTO) {
        final Transaccion transaccion = new Transaccion();
        mapToEntity(transaccionDTO, transaccion);
        return transaccionRepository.save(transaccion).getTceId();
    }

    public void update(final Integer tceId, final TransaccionDTO transaccionDTO) {
        final Transaccion transaccion = transaccionRepository.findById(tceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transaccionDTO, transaccion);
        transaccionRepository.save(transaccion);
    }

    public void delete(final Integer tceId) {
        transaccionRepository.deleteById(tceId);
    }

    private TransaccionDTO mapToDTO(final Transaccion transaccion,
            final TransaccionDTO transaccionDTO) {
        transaccionDTO.setTceId(transaccion.getTceId());
        transaccionDTO.setTceFechaTransaccion(transaccion.getTceFechaTransaccion());
        transaccionDTO.setTceObservaciones(transaccion.getTceObservaciones());
        transaccionDTO.setTceUsr(transaccion.getTceUsr() == null ? null : transaccion.getTceUsr().getUsrId());
        transaccionDTO.setTceTvo(transaccion.getTceTvo() == null ? null : transaccion.getTceTvo().getTvoId());
        return transaccionDTO;
    }

    private Transaccion mapToEntity(final TransaccionDTO transaccionDTO,
            final Transaccion transaccion) {
        transaccion.setTceFechaTransaccion(transaccionDTO.getTceFechaTransaccion());
        transaccion.setTceObservaciones(transaccionDTO.getTceObservaciones());
        final Usuario tceUsr = transaccionDTO.getTceUsr() == null ? null : usuarioRepository.findById(transaccionDTO.getTceUsr())
                .orElseThrow(() -> new NotFoundException("tceUsr not found"));
        transaccion.setTceUsr(tceUsr);
        final TransaccionEvento tceTvo = transaccionDTO.getTceTvo() == null ? null : transaccionEventoRepository.findById(transaccionDTO.getTceTvo())
                .orElseThrow(() -> new NotFoundException("tceTvo not found"));
        transaccion.setTceTvo(tceTvo);
        return transaccion;
    }

    public ReferencedWarning getReferencedWarning(final Integer tceId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Transaccion transaccion = transaccionRepository.findById(tceId)
                .orElseThrow(NotFoundException::new);
        final TransaccionesProducto tcoTceTransaccionesProducto = transaccionesProductoRepository.findFirstByTcoTce(transaccion);
        if (tcoTceTransaccionesProducto != null) {
            referencedWarning.setKey("transaccion.transaccionesProducto.tcoTce.referenced");
            referencedWarning.addParam(tcoTceTransaccionesProducto.getTcoId());
            return referencedWarning;
        }
        return null;
    }

}
