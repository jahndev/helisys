package io.sisin.sisin.repos;

import io.sisin.sisin.domain.AlmacenEstante;
import io.sisin.sisin.domain.AlmacenRepisa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlmacenRepisaRepository extends JpaRepository<AlmacenRepisa, Integer> {

    AlmacenRepisa findFirstByAmrAmt(AlmacenEstante almacenEstante);

}
