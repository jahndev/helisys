package io.sisin.sisin.service;

import io.sisin.sisin.domain.PedidosCompra;
import io.sisin.sisin.domain.PedidosProducto;
import io.sisin.sisin.domain.Proveedor;
import io.sisin.sisin.domain.Usuario;
import io.sisin.sisin.model.PedidosCompraDTO;
import io.sisin.sisin.repos.PedidosCompraRepository;
import io.sisin.sisin.repos.PedidosProductoRepository;
import io.sisin.sisin.repos.ProveedorRepository;
import io.sisin.sisin.repos.UsuarioRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PedidosCompraService {

    private final PedidosCompraRepository pedidosCompraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProveedorRepository proveedorRepository;
    private final PedidosProductoRepository pedidosProductoRepository;

    public PedidosCompraService(final PedidosCompraRepository pedidosCompraRepository,
            final UsuarioRepository usuarioRepository,
            final ProveedorRepository proveedorRepository,
            final PedidosProductoRepository pedidosProductoRepository) {
        this.pedidosCompraRepository = pedidosCompraRepository;
        this.usuarioRepository = usuarioRepository;
        this.proveedorRepository = proveedorRepository;
        this.pedidosProductoRepository = pedidosProductoRepository;
    }

    public List<PedidosCompraDTO> findAll() {
        final List<PedidosCompra> pedidosCompras = pedidosCompraRepository.findAll(Sort.by("pcaId"));
        return pedidosCompras.stream()
                .map(pedidosCompra -> mapToDTO(pedidosCompra, new PedidosCompraDTO()))
                .toList();
    }

    public PedidosCompraDTO get(final Integer pcaId) {
        return pedidosCompraRepository.findById(pcaId)
                .map(pedidosCompra -> mapToDTO(pedidosCompra, new PedidosCompraDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PedidosCompraDTO pedidosCompraDTO) {
        final PedidosCompra pedidosCompra = new PedidosCompra();
        mapToEntity(pedidosCompraDTO, pedidosCompra);
        return pedidosCompraRepository.save(pedidosCompra).getPcaId();
    }

    public void update(final Integer pcaId, final PedidosCompraDTO pedidosCompraDTO) {
        final PedidosCompra pedidosCompra = pedidosCompraRepository.findById(pcaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pedidosCompraDTO, pedidosCompra);
        pedidosCompraRepository.save(pedidosCompra);
    }

    public void delete(final Integer pcaId) {
        pedidosCompraRepository.deleteById(pcaId);
    }

    private PedidosCompraDTO mapToDTO(final PedidosCompra pedidosCompra,
            final PedidosCompraDTO pedidosCompraDTO) {
        pedidosCompraDTO.setPcaId(pedidosCompra.getPcaId());
        pedidosCompraDTO.setPcaDescripcion(pedidosCompra.getPcaDescripcion());
        pedidosCompraDTO.setPcaFechaPedido(pedidosCompra.getPcaFechaPedido());
        pedidosCompraDTO.setPcaFechaEnvio(pedidosCompra.getPcaFechaEnvio());
        pedidosCompraDTO.setPcaFechaEntrega(pedidosCompra.getPcaFechaEntrega());
        pedidosCompraDTO.setPcaFechaPrometida(pedidosCompra.getPcaFechaPrometida());
        pedidosCompraDTO.setPcaDireccionEnvio(pedidosCompra.getPcaDireccionEnvio());
        pedidosCompraDTO.setPcaUsr(pedidosCompra.getPcaUsr() == null ? null : pedidosCompra.getPcaUsr().getUsrId());
        pedidosCompraDTO.setPcaPve(pedidosCompra.getPcaPve() == null ? null : pedidosCompra.getPcaPve().getPveId());
        return pedidosCompraDTO;
    }

    private PedidosCompra mapToEntity(final PedidosCompraDTO pedidosCompraDTO,
            final PedidosCompra pedidosCompra) {
        pedidosCompra.setPcaDescripcion(pedidosCompraDTO.getPcaDescripcion());
        pedidosCompra.setPcaFechaPedido(pedidosCompraDTO.getPcaFechaPedido());
        pedidosCompra.setPcaFechaEnvio(pedidosCompraDTO.getPcaFechaEnvio());
        pedidosCompra.setPcaFechaEntrega(pedidosCompraDTO.getPcaFechaEntrega());
        pedidosCompra.setPcaFechaPrometida(pedidosCompraDTO.getPcaFechaPrometida());
        pedidosCompra.setPcaDireccionEnvio(pedidosCompraDTO.getPcaDireccionEnvio());
        final Usuario pcaUsr = pedidosCompraDTO.getPcaUsr() == null ? null : usuarioRepository.findById(pedidosCompraDTO.getPcaUsr())
                .orElseThrow(() -> new NotFoundException("pcaUsr not found"));
        pedidosCompra.setPcaUsr(pcaUsr);
        final Proveedor pcaPve = pedidosCompraDTO.getPcaPve() == null ? null : proveedorRepository.findById(pedidosCompraDTO.getPcaPve())
                .orElseThrow(() -> new NotFoundException("pcaPve not found"));
        pedidosCompra.setPcaPve(pcaPve);
        return pedidosCompra;
    }

    public ReferencedWarning getReferencedWarning(final Integer pcaId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PedidosCompra pedidosCompra = pedidosCompraRepository.findById(pcaId)
                .orElseThrow(NotFoundException::new);
        final PedidosProducto pptPcaPedidosProducto = pedidosProductoRepository.findFirstByPptPca(pedidosCompra);
        if (pptPcaPedidosProducto != null) {
            referencedWarning.setKey("pedidosCompra.pedidosProducto.pptPca.referenced");
            referencedWarning.addParam(pptPcaPedidosProducto.getPptId());
            return referencedWarning;
        }
        return null;
    }

}
