package io.sisin.sisin.service;

import io.sisin.sisin.domain.ContactoProveedor;
import io.sisin.sisin.domain.PedidosCompra;
import io.sisin.sisin.domain.Proveedor;
import io.sisin.sisin.model.ProveedorDTO;
import io.sisin.sisin.repos.ContactoProveedorRepository;
import io.sisin.sisin.repos.PedidosCompraRepository;
import io.sisin.sisin.repos.ProveedorRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final PedidosCompraRepository pedidosCompraRepository;
    private final ContactoProveedorRepository contactoProveedorRepository;

    public ProveedorService(final ProveedorRepository proveedorRepository,
            final PedidosCompraRepository pedidosCompraRepository,
            final ContactoProveedorRepository contactoProveedorRepository) {
        this.proveedorRepository = proveedorRepository;
        this.pedidosCompraRepository = pedidosCompraRepository;
        this.contactoProveedorRepository = contactoProveedorRepository;
    }

    public List<ProveedorDTO> findAll() {
        final List<Proveedor> proveedors = proveedorRepository.findAll(Sort.by("pveId"));
        return proveedors.stream()
                .map(proveedor -> mapToDTO(proveedor, new ProveedorDTO()))
                .toList();
    }

    public ProveedorDTO get(final Integer pveId) {
        return proveedorRepository.findById(pveId)
                .map(proveedor -> mapToDTO(proveedor, new ProveedorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProveedorDTO proveedorDTO) {
        final Proveedor proveedor = new Proveedor();
        mapToEntity(proveedorDTO, proveedor);
        return proveedorRepository.save(proveedor).getPveId();
    }

    public void update(final Integer pveId, final ProveedorDTO proveedorDTO) {
        final Proveedor proveedor = proveedorRepository.findById(pveId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(proveedorDTO, proveedor);
        proveedorRepository.save(proveedor);
    }

    public void delete(final Integer pveId) {
        proveedorRepository.deleteById(pveId);
    }

    private ProveedorDTO mapToDTO(final Proveedor proveedor, final ProveedorDTO proveedorDTO) {
        proveedorDTO.setPveId(proveedor.getPveId());
        proveedorDTO.setPveNombre(proveedor.getPveNombre());
        proveedorDTO.setPveTelefono(proveedor.getPveTelefono());
        proveedorDTO.setPveFax(proveedor.getPveFax());
        proveedorDTO.setPveEmail(proveedor.getPveEmail());
        proveedorDTO.setPveDireccion(proveedor.getPveDireccion());
        return proveedorDTO;
    }

    private Proveedor mapToEntity(final ProveedorDTO proveedorDTO, final Proveedor proveedor) {
        proveedor.setPveNombre(proveedorDTO.getPveNombre());
        proveedor.setPveTelefono(proveedorDTO.getPveTelefono());
        proveedor.setPveFax(proveedorDTO.getPveFax());
        proveedor.setPveEmail(proveedorDTO.getPveEmail());
        proveedor.setPveDireccion(proveedorDTO.getPveDireccion());
        return proveedor;
    }

    public ReferencedWarning getReferencedWarning(final Integer pveId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Proveedor proveedor = proveedorRepository.findById(pveId)
                .orElseThrow(NotFoundException::new);
        final PedidosCompra pcaPvePedidosCompra = pedidosCompraRepository.findFirstByPcaPve(proveedor);
        if (pcaPvePedidosCompra != null) {
            referencedWarning.setKey("proveedor.pedidosCompra.pcaPve.referenced");
            referencedWarning.addParam(pcaPvePedidosCompra.getPcaId());
            return referencedWarning;
        }
        final ContactoProveedor cpePveContactoProveedor = contactoProveedorRepository.findFirstByCpePve(proveedor);
        if (cpePveContactoProveedor != null) {
            referencedWarning.setKey("proveedor.contactoProveedor.cpePve.referenced");
            referencedWarning.addParam(cpePveContactoProveedor.getCveId());
            return referencedWarning;
        }
        return null;
    }

}
