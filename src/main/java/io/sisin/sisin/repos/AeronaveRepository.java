package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Aeronave;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AeronaveRepository extends JpaRepository<Aeronave, Integer> {

    boolean existsByAnvMatriculaIgnoreCase(String anvMatricula);

}
