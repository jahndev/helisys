package io.sisin.sisin.service;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.ModeloAeronave;
import io.sisin.sisin.model.ModeloAeronaveDTO;
import io.sisin.sisin.repos.AeronaveRepository;
import io.sisin.sisin.repos.ModeloAeronaveRepository;
import io.sisin.sisin.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ModeloAeronaveService {

    private final ModeloAeronaveRepository modeloAeronaveRepository;
    private final AeronaveRepository aeronaveRepository;

    public ModeloAeronaveService(final ModeloAeronaveRepository modeloAeronaveRepository,
            final AeronaveRepository aeronaveRepository) {
        this.modeloAeronaveRepository = modeloAeronaveRepository;
        this.aeronaveRepository = aeronaveRepository;
    }

    public List<ModeloAeronaveDTO> findAll() {
        final List<ModeloAeronave> modeloAeronaves = modeloAeronaveRepository.findAll(Sort.by("mre"));
        return modeloAeronaves.stream()
                .map(modeloAeronave -> mapToDTO(modeloAeronave, new ModeloAeronaveDTO()))
                .toList();
    }

    public ModeloAeronaveDTO get(final Integer mre) {
        return modeloAeronaveRepository.findById(mre)
                .map(modeloAeronave -> mapToDTO(modeloAeronave, new ModeloAeronaveDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ModeloAeronaveDTO modeloAeronaveDTO) {
        final ModeloAeronave modeloAeronave = new ModeloAeronave();
        mapToEntity(modeloAeronaveDTO, modeloAeronave);
        return modeloAeronaveRepository.save(modeloAeronave).getMre();
    }

    public void update(final Integer mre, final ModeloAeronaveDTO modeloAeronaveDTO) {
        final ModeloAeronave modeloAeronave = modeloAeronaveRepository.findById(mre)
                .orElseThrow(NotFoundException::new);
        mapToEntity(modeloAeronaveDTO, modeloAeronave);
        modeloAeronaveRepository.save(modeloAeronave);
    }

    public void delete(final Integer mre) {
        modeloAeronaveRepository.deleteById(mre);
    }

    private ModeloAeronaveDTO mapToDTO(final ModeloAeronave modeloAeronave,
            final ModeloAeronaveDTO modeloAeronaveDTO) {
        modeloAeronaveDTO.setMre(modeloAeronave.getMre());
        modeloAeronaveDTO.setMreNombre(modeloAeronave.getMreNombre());
        modeloAeronaveDTO.setMreAnv(modeloAeronave.getMreAnv() == null ? null : modeloAeronave.getMreAnv().getAnvId());
        return modeloAeronaveDTO;
    }

    private ModeloAeronave mapToEntity(final ModeloAeronaveDTO modeloAeronaveDTO,
            final ModeloAeronave modeloAeronave) {
        modeloAeronave.setMreNombre(modeloAeronaveDTO.getMreNombre());
        final Aeronave mreAnv = modeloAeronaveDTO.getMreAnv() == null ? null : aeronaveRepository.findById(modeloAeronaveDTO.getMreAnv())
                .orElseThrow(() -> new NotFoundException("mreAnv not found"));
        modeloAeronave.setMreAnv(mreAnv);
        return modeloAeronave;
    }

    public boolean mreNombreExists(final String mreNombre) {
        return modeloAeronaveRepository.existsByMreNombreIgnoreCase(mreNombre);
    }

}
