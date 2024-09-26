package io.sisin.sisin.service;

import io.sisin.sisin.domain.AlmacenRepisa;
import io.sisin.sisin.domain.PedidosProducto;
import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.domain.TipoProducto;
import io.sisin.sisin.domain.TransaccionesProducto;
import io.sisin.sisin.model.ProductoDTO;
import io.sisin.sisin.repos.AlmacenRepisaRepository;
import io.sisin.sisin.repos.PedidosProductoRepository;
import io.sisin.sisin.repos.ProductoRepository;
import io.sisin.sisin.repos.TipoProductoRepository;
import io.sisin.sisin.repos.TransaccionesProductoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final AlmacenRepisaRepository almacenRepisaRepository;
    private final TipoProductoRepository tipoProductoRepository;
    private final TransaccionesProductoRepository transaccionesProductoRepository;
    private final PedidosProductoRepository pedidosProductoRepository;

    public ProductoService(final ProductoRepository productoRepository,
            final AlmacenRepisaRepository almacenRepisaRepository,
            final TipoProductoRepository tipoProductoRepository,
            final TransaccionesProductoRepository transaccionesProductoRepository,
            final PedidosProductoRepository pedidosProductoRepository) {
        this.productoRepository = productoRepository;
        this.almacenRepisaRepository = almacenRepisaRepository;
        this.tipoProductoRepository = tipoProductoRepository;
        this.transaccionesProductoRepository = transaccionesProductoRepository;
        this.pedidosProductoRepository = pedidosProductoRepository;
    }

    public List<ProductoDTO> findAll() {
        final List<Producto> productos = productoRepository.findAll(Sort.by("proId"));
        return productos.stream()
                .map(producto -> mapToDTO(producto, new ProductoDTO()))
                .toList();
    }

    public ProductoDTO get(final Integer proId) {
        return productoRepository.findById(proId)
                .map(producto -> mapToDTO(producto, new ProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductoDTO productoDTO) {
        final Producto producto = new Producto();
        mapToEntity(productoDTO, producto);
        return productoRepository.save(producto).getProId();
    }

    public void update(final Integer proId, final ProductoDTO productoDTO) {
        final Producto producto = productoRepository.findById(proId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productoDTO, producto);
        productoRepository.save(producto);
    }

    public void delete(final Integer proId) {
        productoRepository.deleteById(proId);
    }

    private ProductoDTO mapToDTO(final Producto producto, final ProductoDTO productoDTO) {
        productoDTO.setProId(producto.getProId());
        productoDTO.setProNumeroParte(producto.getProNumeroParte());
        productoDTO.setProNombre(producto.getProNombre());
        productoDTO.setProNumeroParteAlterno(producto.getProNumeroParteAlterno());
        productoDTO.setProNumeroSerie(producto.getProNumeroSerie());
        productoDTO.setProUnidades(producto.getProUnidades());
        productoDTO.setProFechaVencimiento(producto.getProFechaVencimiento());
        productoDTO.setProTipoDocumento(producto.getProTipoDocumento());
        productoDTO.setProAmr(producto.getProAmr() == null ? null : producto.getProAmr().getAmrId());
        productoDTO.setProTpoIdo(producto.getProTpoIdo() == null ? null : producto.getProTpoIdo().getTpoIdo());
        return productoDTO;
    }

    private Producto mapToEntity(final ProductoDTO productoDTO, final Producto producto) {
        producto.setProNumeroParte(productoDTO.getProNumeroParte());
        producto.setProNombre(productoDTO.getProNombre());
        producto.setProNumeroParteAlterno(productoDTO.getProNumeroParteAlterno());
        producto.setProNumeroSerie(productoDTO.getProNumeroSerie());
        producto.setProUnidades(productoDTO.getProUnidades());
        producto.setProFechaVencimiento(productoDTO.getProFechaVencimiento());
        producto.setProTipoDocumento(productoDTO.getProTipoDocumento());
        final AlmacenRepisa proAmr = productoDTO.getProAmr() == null ? null : almacenRepisaRepository.findById(productoDTO.getProAmr())
                .orElseThrow(() -> new NotFoundException("proAmr not found"));
        producto.setProAmr(proAmr);
        final TipoProducto proTpoIdo = productoDTO.getProTpoIdo() == null ? null : tipoProductoRepository.findById(productoDTO.getProTpoIdo())
                .orElseThrow(() -> new NotFoundException("proTpoIdo not found"));
        producto.setProTpoIdo(proTpoIdo);
        return producto;
    }

    public boolean proNumeroParteAlternoExists(final String proNumeroParteAlterno) {
        return productoRepository.existsByProNumeroParteAlternoIgnoreCase(proNumeroParteAlterno);
    }

    public boolean proNumeroSerieExists(final String proNumeroSerie) {
        return productoRepository.existsByProNumeroSerieIgnoreCase(proNumeroSerie);
    }

    public ReferencedWarning getReferencedWarning(final Integer proId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Producto producto = productoRepository.findById(proId)
                .orElseThrow(NotFoundException::new);
        final TransaccionesProducto tcoProTransaccionesProducto = transaccionesProductoRepository.findFirstByTcoPro(producto);
        if (tcoProTransaccionesProducto != null) {
            referencedWarning.setKey("producto.transaccionesProducto.tcoPro.referenced");
            referencedWarning.addParam(tcoProTransaccionesProducto.getTcoId());
            return referencedWarning;
        }
        final PedidosProducto pptProPedidosProducto = pedidosProductoRepository.findFirstByPptPro(producto);
        if (pptProPedidosProducto != null) {
            referencedWarning.setKey("producto.pedidosProducto.pptPro.referenced");
            referencedWarning.addParam(pptProPedidosProducto.getPptId());
            return referencedWarning;
        }
        return null;
    }

}
