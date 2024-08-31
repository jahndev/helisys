package io.sisin.sisin.service;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.ModeloAeronave;
import io.sisin.sisin.domain.TrasaccionEvento;
import io.sisin.sisin.model.AeronaveDTO;
import io.sisin.sisin.repos.AeronaveRepository;
import io.sisin.sisin.repos.ModeloAeronaveRepository;
import io.sisin.sisin.repos.TrasaccionEventoRepository;
import io.sisin.sisin.util.NotFoundException;
import io.sisin.sisin.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AeronaveService {

    private final AeronaveRepository aeronaveRepository;
    private final TrasaccionEventoRepository trasaccionEventoRepository;
    private final ModeloAeronaveRepository modeloAeronaveRepository;

    public AeronaveService(final AeronaveRepository aeronaveRepository,
            final TrasaccionEventoRepository trasaccionEventoRepository,
            final ModeloAeronaveRepository modeloAeronaveRepository) {
        this.aeronaveRepository = aeronaveRepository;
        this.trasaccionEventoRepository = trasaccionEventoRepository;
        this.modeloAeronaveRepository = modeloAeronaveRepository;
    }

    public List<AeronaveDTO> findAll() {
        final List<Aeronave> aeronaves = aeronaveRepository.findAll(Sort.by("anvId"));
        return aeronaves.stream()
                .map(aeronave -> mapToDTO(aeronave, new AeronaveDTO()))
                .toList();
    }

    public AeronaveDTO get(final Integer anvId) {
        return aeronaveRepository.findById(anvId)
                .map(aeronave -> mapToDTO(aeronave, new AeronaveDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AeronaveDTO aeronaveDTO) {
        final Aeronave aeronave = new Aeronave();
        mapToEntity(aeronaveDTO, aeronave);
        return aeronaveRepository.save(aeronave).getAnvId();
    }

    public void update(final Integer anvId, final AeronaveDTO aeronaveDTO) {
        final Aeronave aeronave = aeronaveRepository.findById(anvId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(aeronaveDTO, aeronave);
        aeronaveRepository.save(aeronave);
    }

    public void delete(final Integer anvId) {
        aeronaveRepository.deleteById(anvId);
    }

    private AeronaveDTO mapToDTO(final Aeronave aeronave, final AeronaveDTO aeronaveDTO) {
        aeronaveDTO.setAnvId(aeronave.getAnvId());
        aeronaveDTO.setAnvMatricula(aeronave.getAnvMatricula());
        aeronaveDTO.setAnvNombre(aeronave.getAnvNombre());
        return aeronaveDTO;
    }

    private Aeronave mapToEntity(final AeronaveDTO aeronaveDTO, final Aeronave aeronave) {
        aeronave.setAnvMatricula(aeronaveDTO.getAnvMatricula());
        aeronave.setAnvNombre(aeronaveDTO.getAnvNombre());
        return aeronave;
    }

    public boolean anvMatriculaExists(final String anvMatricula) {
        return aeronaveRepository.existsByAnvMatriculaIgnoreCase(anvMatricula);
    }

    public ReferencedWarning getReferencedWarning(final Integer anvId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Aeronave aeronave = aeronaveRepository.findById(anvId)
                .orElseThrow(NotFoundException::new);
        final TrasaccionEvento aeronavesAnvTrasaccionEvento = trasaccionEventoRepository.findFirstByAeronavesAnv(aeronave);
        if (aeronavesAnvTrasaccionEvento != null) {
            referencedWarning.setKey("aeronave.trasaccionEvento.aeronavesAnv.referenced");
            referencedWarning.addParam(aeronavesAnvTrasaccionEvento.getTvoId());
            return referencedWarning;
        }
        final ModeloAeronave mreAnvModeloAeronave = modeloAeronaveRepository.findFirstByMreAnv(aeronave);
        if (mreAnvModeloAeronave != null) {
            referencedWarning.setKey("aeronave.modeloAeronave.mreAnv.referenced");
            referencedWarning.addParam(mreAnvModeloAeronave.getMre());
            return referencedWarning;
        }
        return null;
    }

}
