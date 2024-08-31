package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Aeronave;
import io.sisin.sisin.domain.TrasaccionEvento;
import io.sisin.sisin.domain.TrasaccionTipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrasaccionEventoRepository extends JpaRepository<TrasaccionEvento, Integer> {

    TrasaccionEvento findFirstByTvoTte(TrasaccionTipoEvento trasaccionTipoEvento);

    TrasaccionEvento findFirstByAeronavesAnv(Aeronave aeronave);

}
