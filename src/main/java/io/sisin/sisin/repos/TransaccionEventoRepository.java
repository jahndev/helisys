package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.TransaccionEvento;
import io.sisin.sisin.domain.TransaccionTipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransaccionEventoRepository extends JpaRepository<TransaccionEvento, Integer> {

    TransaccionEvento findFirstByTvoTte(TransaccionTipoEvento transaccionTipoEvento);

    TransaccionEvento findFirstByAeronavesAnv(Aeronave aeronave);

}
