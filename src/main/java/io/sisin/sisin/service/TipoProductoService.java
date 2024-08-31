package io.sisin.sisin.service;

import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.domain.TipoProducto;
import io.sisin.sisin.model.TipoProductoDTO;
import io.sisin.sisin.repos.ProductoRepository;
import io.sisin.sisin.repos.TipoProductoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository;
    private final ProductoRepository productoRepository;

    public TipoProductoService(final TipoProductoRepository tipoProductoRepository,
            final ProductoRepository productoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
        this.productoRepository = productoRepository;
    }

    public List<TipoProductoDTO> findAll() {
        final List<TipoProducto> tipoProductoes = tipoProductoRepository.findAll(Sort.by("tpoIdo"));
        return tipoProductoes.stream()
                .map(tipoProducto -> mapToDTO(tipoProducto, new TipoProductoDTO()))
                .toList();
    }

    public TipoProductoDTO get(final Integer tpoIdo) {
        return tipoProductoRepository.findById(tpoIdo)
                .map(tipoProducto -> mapToDTO(tipoProducto, new TipoProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TipoProductoDTO tipoProductoDTO) {
        final TipoProducto tipoProducto = new TipoProducto();
        mapToEntity(tipoProductoDTO, tipoProducto);
        return tipoProductoRepository.save(tipoProducto).getTpoIdo();
    }

    public void update(final Integer tpoIdo, final TipoProductoDTO tipoProductoDTO) {
        final TipoProducto tipoProducto = tipoProductoRepository.findById(tpoIdo)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tipoProductoDTO, tipoProducto);
        tipoProductoRepository.save(tipoProducto);
    }

    public void delete(final Integer tpoIdo) {
        tipoProductoRepository.deleteById(tpoIdo);
    }

    private TipoProductoDTO mapToDTO(final TipoProducto tipoProducto,
            final TipoProductoDTO tipoProductoDTO) {
        tipoProductoDTO.setTpoIdo(tipoProducto.getTpoIdo());
        tipoProductoDTO.setTpoNombreTipo(tipoProducto.getTpoNombreTipo());
        return tipoProductoDTO;
    }

    private TipoProducto mapToEntity(final TipoProductoDTO tipoProductoDTO,
            final TipoProducto tipoProducto) {
        tipoProducto.setTpoNombreTipo(tipoProductoDTO.getTpoNombreTipo());
        return tipoProducto;
    }

    public ReferencedWarning getReferencedWarning(final Integer tpoIdo) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TipoProducto tipoProducto = tipoProductoRepository.findById(tpoIdo)
                .orElseThrow(NotFoundException::new);
        final Producto proTpoIdoProducto = productoRepository.findFirstByProTpoIdo(tipoProducto);
        if (proTpoIdoProducto != null) {
            referencedWarning.setKey("tipoProducto.producto.proTpoIdo.referenced");
            referencedWarning.addParam(proTpoIdoProducto.getProId());
            return referencedWarning;
        }
        return null;
    }

}
