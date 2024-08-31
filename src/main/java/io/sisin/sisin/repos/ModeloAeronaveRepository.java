package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.ModeloAeronave;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModeloAeronaveRepository extends JpaRepository<ModeloAeronave, Integer> {

    ModeloAeronave findFirstByMreAnv(Aeronave aeronave);

    boolean existsByMreNombreIgnoreCase(String mreNombre);

}
