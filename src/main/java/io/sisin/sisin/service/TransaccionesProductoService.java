package io.sisin.sisin.service;

import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.domain.Transaccion;
import io.sisin.sisin.domain.TransaccionesProducto;
import io.sisin.sisin.model.TransaccionesProductoDTO;
import io.sisin.sisin.repos.ProductoRepository;
import io.sisin.sisin.repos.TransaccionRepository;
import io.sisin.sisin.repos.TransaccionesProductoRepository;
import io.sisin.sisin.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransaccionesProductoService {

    private final TransaccionesProductoRepository transaccionesProductoRepository;
    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;

    public TransaccionesProductoService(
            final TransaccionesProductoRepository transaccionesProductoRepository,
            final TransaccionRepository transaccionRepository,
            final ProductoRepository productoRepository) {
        this.transaccionesProductoRepository = transaccionesProductoRepository;
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
    }

    public List<TransaccionesProductoDTO> findAll() {
        final List<TransaccionesProducto> transaccionesProductoes = transaccionesProductoRepository.findAll(Sort.by("tcoId"));
        return transaccionesProductoes.stream()
                .map(transaccionesProducto -> mapToDTO(transaccionesProducto, new TransaccionesProductoDTO()))
                .toList();
    }

    public TransaccionesProductoDTO get(final Integer tcoId) {
        return transaccionesProductoRepository.findById(tcoId)
                .map(transaccionesProducto -> mapToDTO(transaccionesProducto, new TransaccionesProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TransaccionesProductoDTO transaccionesProductoDTO) {
        final TransaccionesProducto transaccionesProducto = new TransaccionesProducto();
        mapToEntity(transaccionesProductoDTO, transaccionesProducto);
        return transaccionesProductoRepository.save(transaccionesProducto).getTcoId();
    }

    public void update(final Integer tcoId,
            final TransaccionesProductoDTO transaccionesProductoDTO) {
        final TransaccionesProducto transaccionesProducto = transaccionesProductoRepository.findById(tcoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transaccionesProductoDTO, transaccionesProducto);
        transaccionesProductoRepository.save(transaccionesProducto);
    }

    public void delete(final Integer tcoId) {
        transaccionesProductoRepository.deleteById(tcoId);
    }

    private TransaccionesProductoDTO mapToDTO(final TransaccionesProducto transaccionesProducto,
            final TransaccionesProductoDTO transaccionesProductoDTO) {
        transaccionesProductoDTO.setTcoId(transaccionesProducto.getTcoId());
        transaccionesProductoDTO.setTcoUnidades(transaccionesProducto.getTcoUnidades());
        transaccionesProductoDTO.setTcoTce(transaccionesProducto.getTcoTce() == null ? null : transaccionesProducto.getTcoTce().getTceId());
        transaccionesProductoDTO.setTcoPro(transaccionesProducto.getTcoPro() == null ? null : transaccionesProducto.getTcoPro().getProId());
        return transaccionesProductoDTO;
    }

    private TransaccionesProducto mapToEntity(
            final TransaccionesProductoDTO transaccionesProductoDTO,
            final TransaccionesProducto transaccionesProducto) {
        transaccionesProducto.setTcoUnidades(transaccionesProductoDTO.getTcoUnidades());
        final Transaccion tcoTce = transaccionesProductoDTO.getTcoTce() == null ? null : transaccionRepository.findById(transaccionesProductoDTO.getTcoTce())
                .orElseThrow(() -> new NotFoundException("tcoTce not found"));
        transaccionesProducto.setTcoTce(tcoTce);
        final Producto tcoPro = transaccionesProductoDTO.getTcoPro() == null ? null : productoRepository.findById(transaccionesProductoDTO.getTcoPro())
                .orElseThrow(() -> new NotFoundException("tcoPro not found"));
        transaccionesProducto.setTcoPro(tcoPro);
        return transaccionesProducto;
    }

}
