package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Transaccion;
import io.sisin.sisin.domain.TrasaccionEvento;
import io.sisin.sisin.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

    Transaccion findFirstByTceUsr(Usuario usuario);

    Transaccion findFirstByTceTvo(TrasaccionEvento trasaccionEvento);

}
