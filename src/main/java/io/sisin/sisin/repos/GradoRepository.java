package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Grado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GradoRepository extends JpaRepository<Grado, Integer> {

    boolean existsByGdoNombreIgnoreCase(String gdoNombre);

}
