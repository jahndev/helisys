package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Brigada;
import io.sisin.sisin.domain.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UnidadRepository extends JpaRepository<Unidad, Integer> {

    Unidad findFirstByUndBga(Brigada brigada);

}
