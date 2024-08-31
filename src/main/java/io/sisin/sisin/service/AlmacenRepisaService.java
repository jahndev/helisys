package io.sisin.sisin.service;

import io.sisin.sisin.domain.AlmacenEstante;
import io.sisin.sisin.domain.AlmacenRepisa;
import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.model.AlmacenRepisaDTO;
import io.sisin.sisin.repos.AlmacenEstanteRepository;
import io.sisin.sisin.repos.AlmacenRepisaRepository;
import io.sisin.sisin.repos.ProductoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AlmacenRepisaService {

    private final AlmacenRepisaRepository almacenRepisaRepository;
    private final AlmacenEstanteRepository almacenEstanteRepository;
    private final ProductoRepository productoRepository;

    public AlmacenRepisaService(final AlmacenRepisaRepository almacenRepisaRepository,
            final AlmacenEstanteRepository almacenEstanteRepository,
            final ProductoRepository productoRepository) {
        this.almacenRepisaRepository = almacenRepisaRepository;
        this.almacenEstanteRepository = almacenEstanteRepository;
        this.productoRepository = productoRepository;
    }

    public List<AlmacenRepisaDTO> findAll() {
        final List<AlmacenRepisa> almacenRepisas = almacenRepisaRepository.findAll(Sort.by("amrId"));
        return almacenRepisas.stream()
                .map(almacenRepisa -> mapToDTO(almacenRepisa, new AlmacenRepisaDTO()))
                .toList();
    }

    public AlmacenRepisaDTO get(final Integer amrId) {
        return almacenRepisaRepository.findById(amrId)
                .map(almacenRepisa -> mapToDTO(almacenRepisa, new AlmacenRepisaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AlmacenRepisaDTO almacenRepisaDTO) {
        final AlmacenRepisa almacenRepisa = new AlmacenRepisa();
        mapToEntity(almacenRepisaDTO, almacenRepisa);
        return almacenRepisaRepository.save(almacenRepisa).getAmrId();
    }

    public void update(final Integer amrId, final AlmacenRepisaDTO almacenRepisaDTO) {
        final AlmacenRepisa almacenRepisa = almacenRepisaRepository.findById(amrId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(almacenRepisaDTO, almacenRepisa);
        almacenRepisaRepository.save(almacenRepisa);
    }

    public void delete(final Integer amrId) {
        almacenRepisaRepository.deleteById(amrId);
    }

    private AlmacenRepisaDTO mapToDTO(final AlmacenRepisa almacenRepisa,
            final AlmacenRepisaDTO almacenRepisaDTO) {
        almacenRepisaDTO.setAmrId(almacenRepisa.getAmrId());
        almacenRepisaDTO.setAmrDescripcion(almacenRepisa.getAmrDescripcion());
        almacenRepisaDTO.setAmrAmt(almacenRepisa.getAmrAmt() == null ? null : almacenRepisa.getAmrAmt().getAmtId());
        return almacenRepisaDTO;
    }

    private AlmacenRepisa mapToEntity(final AlmacenRepisaDTO almacenRepisaDTO,
            final AlmacenRepisa almacenRepisa) {
        almacenRepisa.setAmrDescripcion(almacenRepisaDTO.getAmrDescripcion());
        final AlmacenEstante amrAmt = almacenRepisaDTO.getAmrAmt() == null ? null : almacenEstanteRepository.findById(almacenRepisaDTO.getAmrAmt())
                .orElseThrow(() -> new NotFoundException("amrAmt not found"));
        almacenRepisa.setAmrAmt(amrAmt);
        return almacenRepisa;
    }

    public ReferencedWarning getReferencedWarning(final Integer amrId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final AlmacenRepisa almacenRepisa = almacenRepisaRepository.findById(amrId)
                .orElseThrow(NotFoundException::new);
        final Producto proAmrProducto = productoRepository.findFirstByProAmr(almacenRepisa);
        if (proAmrProducto != null) {
            referencedWarning.setKey("almacenRepisa.producto.proAmr.referenced");
            referencedWarning.addParam(proAmrProducto.getProId());
            return referencedWarning;
        }
        return null;
    }

}
