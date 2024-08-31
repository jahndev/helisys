package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Escuadron;
import io.sisin.sisin.domain.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EscuadronRepository extends JpaRepository<Escuadron, Integer> {

    Escuadron findFirstByEdnUnd(Unidad unidad);

}
