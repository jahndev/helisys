package io.sisin.sisin.service;

import io.sisin.sisin.domain.PedidosCompra;
import io.sisin.sisin.domain.PedidosProducto;
import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.model.PedidosProductoDTO;
import io.sisin.sisin.repos.PedidosCompraRepository;
import io.sisin.sisin.repos.PedidosProductoRepository;
import io.sisin.sisin.repos.ProductoRepository;
import io.sisin.sisin.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PedidosProductoService {

    private final PedidosProductoRepository pedidosProductoRepository;
    private final PedidosCompraRepository pedidosCompraRepository;
    private final ProductoRepository productoRepository;

    public PedidosProductoService(final PedidosProductoRepository pedidosProductoRepository,
            final PedidosCompraRepository pedidosCompraRepository,
            final ProductoRepository productoRepository) {
        this.pedidosProductoRepository = pedidosProductoRepository;
        this.pedidosCompraRepository = pedidosCompraRepository;
        this.productoRepository = productoRepository;
    }

    public List<PedidosProductoDTO> findAll() {
        final List<PedidosProducto> pedidosProductos = pedidosProductoRepository.findAll(Sort.by("pptId"));
        return pedidosProductos.stream()
                .map(pedidosProducto -> mapToDTO(pedidosProducto, new PedidosProductoDTO()))
                .toList();
    }

    public PedidosProductoDTO get(final Integer pptId) {
        return pedidosProductoRepository.findById(pptId)
                .map(pedidosProducto -> mapToDTO(pedidosProducto, new PedidosProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PedidosProductoDTO pedidosProductoDTO) {
        final PedidosProducto pedidosProducto = new PedidosProducto();
        mapToEntity(pedidosProductoDTO, pedidosProducto);
        return pedidosProductoRepository.save(pedidosProducto).getPptId();
    }

    public void update(final Integer pptId, final PedidosProductoDTO pedidosProductoDTO) {
        final PedidosProducto pedidosProducto = pedidosProductoRepository.findById(pptId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pedidosProductoDTO, pedidosProducto);
        pedidosProductoRepository.save(pedidosProducto);
    }

    public void delete(final Integer pptId) {
        pedidosProductoRepository.deleteById(pptId);
    }

    private PedidosProductoDTO mapToDTO(final PedidosProducto pedidosProducto,
            final PedidosProductoDTO pedidosProductoDTO) {
        pedidosProductoDTO.setPptId(pedidosProducto.getPptId());
        pedidosProductoDTO.setPptCantidad(pedidosProducto.getPptCantidad());
        pedidosProductoDTO.setPptPrecioUnitario(pedidosProducto.getPptPrecioUnitario());
        pedidosProductoDTO.setPptPca(pedidosProducto.getPptPca() == null ? null : pedidosProducto.getPptPca().getPcaId());
        pedidosProductoDTO.setPptPro(pedidosProducto.getPptPro() == null ? null : pedidosProducto.getPptPro().getProId());
        return pedidosProductoDTO;
    }

    private PedidosProducto mapToEntity(final PedidosProductoDTO pedidosProductoDTO,
            final PedidosProducto pedidosProducto) {
        pedidosProducto.setPptCantidad(pedidosProductoDTO.getPptCantidad());
        pedidosProducto.setPptPrecioUnitario(pedidosProductoDTO.getPptPrecioUnitario());
        final PedidosCompra pptPca = pedidosProductoDTO.getPptPca() == null ? null : pedidosCompraRepository.findById(pedidosProductoDTO.getPptPca())
                .orElseThrow(() -> new NotFoundException("pptPca not found"));
        pedidosProducto.setPptPca(pptPca);
        final Producto pptPro = pedidosProductoDTO.getPptPro() == null ? null : productoRepository.findById(pedidosProductoDTO.getPptPro())
                .orElseThrow(() -> new NotFoundException("pptPro not found"));
        pedidosProducto.setPptPro(pptPro);
        return pedidosProducto;
    }

}
