package io.sisin.sisin.service;

import io.sisin.sisin.domain.ContactoProveedor;
import io.sisin.sisin.domain.Proveedor;
import io.sisin.sisin.model.ContactoProveedorDTO;
import io.sisin.sisin.repos.ContactoProveedorRepository;
import io.sisin.sisin.repos.ProveedorRepository;
import io.sisin.sisin.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ContactoProveedorService {

    private final ContactoProveedorRepository contactoProveedorRepository;
    private final ProveedorRepository proveedorRepository;

    public ContactoProveedorService(final ContactoProveedorRepository contactoProveedorRepository,
            final ProveedorRepository proveedorRepository) {
        this.contactoProveedorRepository = contactoProveedorRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public List<ContactoProveedorDTO> findAll() {
        final List<ContactoProveedor> contactoProveedors = contactoProveedorRepository.findAll(Sort.by("cveId"));
        return contactoProveedors.stream()
                .map(contactoProveedor -> mapToDTO(contactoProveedor, new ContactoProveedorDTO()))
                .toList();
    }

    public ContactoProveedorDTO get(final Integer cveId) {
        return contactoProveedorRepository.findById(cveId)
                .map(contactoProveedor -> mapToDTO(contactoProveedor, new ContactoProveedorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ContactoProveedorDTO contactoProveedorDTO) {
        final ContactoProveedor contactoProveedor = new ContactoProveedor();
        mapToEntity(contactoProveedorDTO, contactoProveedor);
        return contactoProveedorRepository.save(contactoProveedor).getCveId();
    }

    public void update(final Integer cveId, final ContactoProveedorDTO contactoProveedorDTO) {
        final ContactoProveedor contactoProveedor = contactoProveedorRepository.findById(cveId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(contactoProveedorDTO, contactoProveedor);
        contactoProveedorRepository.save(contactoProveedor);
    }

    public void delete(final Integer cveId) {
        contactoProveedorRepository.deleteById(cveId);
    }

    private ContactoProveedorDTO mapToDTO(final ContactoProveedor contactoProveedor,
            final ContactoProveedorDTO contactoProveedorDTO) {
        contactoProveedorDTO.setCveId(contactoProveedor.getCveId());
        contactoProveedorDTO.setCveNombre(contactoProveedor.getCveNombre());
        contactoProveedorDTO.setCpeTelefono(contactoProveedor.getCpeTelefono());
        contactoProveedorDTO.setCpeEmail(contactoProveedor.getCpeEmail());
        contactoProveedorDTO.setCpeUrl(contactoProveedor.getCpeUrl());
        contactoProveedorDTO.setCpePve(contactoProveedor.getCpePve() == null ? null : contactoProveedor.getCpePve().getPveId());
        return contactoProveedorDTO;
    }

    private ContactoProveedor mapToEntity(final ContactoProveedorDTO contactoProveedorDTO,
            final ContactoProveedor contactoProveedor) {
        contactoProveedor.setCveNombre(contactoProveedorDTO.getCveNombre());
        contactoProveedor.setCpeTelefono(contactoProveedorDTO.getCpeTelefono());
        contactoProveedor.setCpeEmail(contactoProveedorDTO.getCpeEmail());
        contactoProveedor.setCpeUrl(contactoProveedorDTO.getCpeUrl());
        final Proveedor cpePve = contactoProveedorDTO.getCpePve() == null ? null : proveedorRepository.findById(contactoProveedorDTO.getCpePve())
                .orElseThrow(() -> new NotFoundException("cpePve not found"));
        contactoProveedor.setCpePve(cpePve);
        return contactoProveedor;
    }

    public boolean cpeEmailExists(final String cpeEmail) {
        return contactoProveedorRepository.existsByCpeEmailIgnoreCase(cpeEmail);
    }

}
